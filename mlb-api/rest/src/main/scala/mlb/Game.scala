package mlb.model

import zio.jdbc.JdbcDecoder
import zio.json._

import java.time.LocalDate

import mlb.model.structure.*

final case class Game(
                       date: GameDate,
                       season: SeasonYear,
                       homeTeam: HomeTeam,
                       awayTeam: AwayTeam,
                       homeScore: HomeScore,
                       awayScore: AwayScore,
                       homeElo: HomeElo,
                       awayElo: AwayElo,
                       homeProbElo: HomeProbElo,
                       awayProbElo: AwayProbElo
                     )

object Game {
  given CanEqual[Game, Game] = CanEqual.derived

  implicit val gameEncoder: JsonEncoder[Game] = DeriveJsonEncoder.gen[Game]
  implicit val gameDecoder: JsonDecoder[Game] = DeriveJsonDecoder.gen[Game]

  def unapply(game: Game): (
    GameDate,
      SeasonYear,
      HomeTeam,
      AwayTeam,
      HomeScore,
      AwayScore,
      HomeElo,
      AwayElo,
      HomeProbElo,
      AwayProbElo
    ) =
    (
      game.date,
      game.season,
      game.homeTeam,
      game.awayTeam,
      game.homeScore,
      game.awayScore,
      game.homeElo,
      game.awayElo,
      game.homeProbElo,
      game.awayProbElo
    )

  // a custom decoder from a tuple
  type Row = (String, Int, String, String, Int, Int, Double, Double, Double, Double)

  extension (g: Game)
  def toRow: Row =
  val (d, y, h, a, hs, as, he, ae, hpe, ape) = Game.unapply(g)
  val row: Row =
    (
      GameDate.unapply(d).toString,
      SeasonYear.unapply(y),
      HomeTeam.unapply(h),
      AwayTeam.unapply(a),
      HomeScore.unapply(hs),
      AwayScore.unapply(as),
      HomeElo.unapply(he),
      AwayElo.unapply(ae),
      HomeProbElo.unapply(hpe),
      AwayProbElo.unapply(ape)
    )
  row

  implicit val jdbcDecoder: JdbcDecoder[Game] = JdbcDecoder[Row]().map[Game] {
    t =>
      val (
        date,
        season,
        home,
        away,
        homeScore,
        awayScore,
        homeElo,
        awayElo,
        homeProbElo,
        awayProbElo
        ) = t
      Game(
        GameDate(LocalDate.parse(date)),
        SeasonYear(season),
        HomeTeam(home),
        AwayTeam(away),
        HomeScore(homeScore),
        AwayScore(awayScore),
        HomeElo(homeElo),
        AwayElo(awayElo),
        HomeProbElo(homeProbElo),
        AwayProbElo(awayProbElo)
      )
  }
}
