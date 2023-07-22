package mlb.api

import mlb.data._
import mlb.model.structure._
import zio.http._
import zio.jdbc.ZConnectionPool
import zio.stream.ZStream
import zio.{ZIO, ZIOAppDefault}
import com.github.tototoshi.csv.defaultCSVFormat

import java.io.File
import java.time.LocalDate

object MlbApi extends ZIOAppDefault {

  // Static endpoints, for testing purpose
  val static: App[Any] = Http
    .collect[Request] {
      case Method.GET -> Root / "text" => Response.text("Hello MLB Fans!")
      case Method.GET -> Root / "json" =>
        Response.json("""{"greetings": "Hello MLB Fans!"}""")
    }
    .withDefaultErrorResponse

  val endpoints: App[ZConnectionPool] = Http
    .collectZIO[Request] {
      // Init the database
      case Method.GET -> Root / "init" =>
        for {
          _ <- DataService.initializeDatabase
          res = Response.text("Database initialized.").withStatus(Status.Ok)
        } yield res

      // Get the latest 20 games between two teams
      case Method.GET -> Root / "game" / "latests" / homeTeam / awayTeam =>
        for {
          game: List[Game] <- DataService.latests(HomeTeam(homeTeam), AwayTeam(awayTeam))
          res: Response = ApiService.latestGameResponse(game)
        } yield res

      //  Predict a match between two teams
      case Method.GET -> Root / "game" / "predict" / homeTeam / awayTeam =>
        for {
          game: List[Game] <- DataService.predictMatch(
            HomeTeam(homeTeam),
            AwayTeam(awayTeam)
          )
          res: Response = ApiService.predictMatchResponse(game, homeTeam, awayTeam)
        } yield res

      // Get the elo of a team
      case Method.GET -> Root / "team" / "elo" / homeTeam =>
        for {
          team: Option[Game] <- DataService.latest(HomeTeam(homeTeam))
          res: Response = ApiService.eloTeamGameResponse(team, homeTeam)
        } yield res

      // Get count of all games in history
      case Method.GET -> Root / "games" / "count" =>
        for {
          count: Option[Int] <- DataService.count
          res: Response = ApiService.countResponse(count)
        } yield res
      // Get all games for a team in history
      case Method.GET -> Root / "games" / "history" / team =>
        for {
          games: List[Game] <- DataService.findHistoryTeam(team)
          res: Response = ApiService.teamHistoryResponse(games)
        } yield res

      case _ =>
        ZIO.succeed(Response.text("Not Found").withStatus(Status.NotFound))
    }
    .withDefaultErrorResponse

  // Init the database and start the server
  val appLogic: ZIO[ZConnectionPool & Server, Throwable, Unit] = for {
    _ <- for {
      conn <- DataService.create
      source <- ZIO.succeed(
        // Read the csv file, change to mlb_elo.csv for full data
        com.github.tototoshi.csv.CSVReader
          .open(new File("mlb_elo_latest.csv"))
      )
      stream <- ZStream
        .fromIterator[Seq[String]](source.iterator)
        // Skip the first row and empty rows
        .filter(row => row.nonEmpty && row(0) != "date")
        .map[Game](row =>
          // Create a game from a row
          Game(
            GameDate(LocalDate.parse(row(0))),
            SeasonYear(row(1).toInt),
            HomeTeam(row(4)),
            AwayTeam(row(5)),
            HomeScore(row(24).toIntOption.getOrElse(-1)),
            AwayScore(row(25).toIntOption.getOrElse(-1)),
            HomeElo(row(6).toDouble),
            AwayElo(row(7).toDouble),
            HomeProbElo(row(8).toDouble),
            AwayProbElo(row(9).toDouble)
          )
        )
        .grouped(1000)
        // Insert 1000 rows at a time to the database
        .foreach(chunk => DataService.insertRows(chunk.toList))
      _ <- ZIO.succeed(source.close())
      res <- ZIO.succeed(conn)
    } yield res
    _ <- Server.serve[ZConnectionPool](static ++ endpoints)
  } yield ()

  // Start the server, equivalent to main method
  override def run: ZIO[Any, Throwable, Unit] =
    appLogic
      .provide(
        DataService.createZIOPoolConfig >>> DataService.connectionPool,
        // Change the port here if needed (default is 8080, mine was already in use)
        Server.defaultWithPort(8088)
      )
}
