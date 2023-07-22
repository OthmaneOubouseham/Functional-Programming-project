package mlb.data

import mlb.model.structure.*
import zio.*
import zio.jdbc.*

// object used for the database
object DataService {

  // Create the database
  val createZIOPoolConfig: ULayer[ZConnectionPoolConfig] =
    ZLayer.succeed(ZConnectionPoolConfig.default)

  val properties: Map[String, String] = Map(
    "user" -> "postgres",
    "password" -> "postgres"
  )

  val connectionPool: ZLayer[ZConnectionPoolConfig, Throwable, ZConnectionPool] =
    ZConnectionPool.h2mem(
      database = "mlb",
      props = properties
    )

  val create: ZIO[ZConnectionPool, Throwable, Unit] = transaction {
    execute(
      sql"CREATE TABLE IF NOT EXISTS games(date DATE NOT NULL, season_year INT NOT NULL, home_team VARCHAR(3), away_team VARCHAR(3), home_score INT, away_score INT, home_elo DOUBLE, away_elo DOUBLE, home_prob_elo DOUBLE, away_prob_elo DOUBLE)"
    )
  }

  def initializeDatabase: ZIO[ZConnectionPool, Throwable, Unit] = {
    transaction {
      execute(
        sql"""CREATE TABLE IF NOT EXISTS games (
                date DATE NOT NULL,
                season_year INT NOT NULL,
                home_team VARCHAR(3),
                away_team VARCHAR(3),
                home_score INT,
                away_score INT,
                home_elo DOUBLE,
                away_elo DOUBLE,
                home_prob_elo DOUBLE,
                away_prob_elo DOUBLE
              )"""
      )
    }
  }

  // Insert a list of games in the database
  def insertRows(
                  games: List[Game]
                ): ZIO[ZConnectionPool, Throwable, UpdateResult] = {
    val rows: List[Game.Row] = games.map(_.toRow)
    transaction {
      insert(
        sql"INSERT INTO games(date, season_year, home_team, away_team, home_score, away_score, home_elo, away_elo, home_prob_elo, away_prob_elo)"
          .values[Game.Row](rows)
      )
    }
  }

  // After are the different queries used in the api

  val count: ZIO[ZConnectionPool, Throwable, Option[Int]] = transaction {
    selectOne(
      sql"SELECT COUNT(*) FROM games".as[Int]
    )
  }

  def latests(
               homeTeam: HomeTeam,
               awayTeam: AwayTeam
             ): ZIO[ZConnectionPool, Throwable, List[Game]] = {
    transaction {
      selectAll(
        sql"SELECT * FROM games WHERE home_team = ${
          HomeTeam
            .unapply(homeTeam)
        } AND away_team = ${AwayTeam.unapply(awayTeam)} ORDER BY date DESC LIMIT 20"
          .as[Game]
      ).map(_.toList)
    }
  }

  def latest(
              homeTeam: HomeTeam
            ): ZIO[ZConnectionPool, Throwable, Option[Game]] = {
    transaction {
      selectOne(
        sql"SELECT * FROM games WHERE home_team = ${
          HomeTeam
            .unapply(homeTeam)
        } OR away_team = ${HomeTeam.unapply(homeTeam)} ORDER BY date DESC LIMIT 1"
          .as[Game]
      )
    }
  }

  def predictMatch(
                    homeTeam: HomeTeam,
                    awayTeam: AwayTeam
                  ): ZIO[ZConnectionPool, Throwable, List[Game]] = {
    transaction {
      selectAll(
        sql"SELECT * FROM games WHERE home_team = ${
          HomeTeam
            .unapply(homeTeam)
        } AND away_team = ${AwayTeam.unapply(awayTeam)} AND home_score != -1 AND away_score != -1 order by date desc limit 20"
          .as[Game]
      ).map(_.toList)
    }
  }

  def findHistoryTeam(
                       homeTeam: String
                     ): ZIO[ZConnectionPool, Throwable, List[Game]] = {
    transaction {
      selectAll(
        sql"SELECT * FROM games WHERE home_team = ${homeTeam}"
          .as[Game]
      ).map(_.toList)
    }
  }
}
