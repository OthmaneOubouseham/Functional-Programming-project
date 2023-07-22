package mlb

import java.time.LocalDate

import zio.json.{JsonDecoder, JsonEncoder}

// AwayElos.scala
object AwayElos {
  opaque type AwayElo = Double
  object AwayElo {
    def apply(value: Double): AwayElo = value
    def unapply(awayElo: AwayElo): Double = awayElo
  }
  given CanEqual[AwayElo, AwayElo] = CanEqual.derived

  implicit val awayEloEncoder: JsonEncoder[AwayElo] = JsonEncoder.double
  implicit val awayEloDecoder: JsonDecoder[AwayElo] = JsonDecoder.double
}

// AwayProbElos.scala
object AwayProbElos {
  opaque type AwayProbElo = Double
  object AwayProbElo {
    def apply(value: Double): AwayProbElo = value
    def unapply(awayProbElo: AwayProbElo): Double = awayProbElo
  }

  given CanEqual[AwayProbElo, AwayProbElo] = CanEqual.derived

  implicit val awayProbEloEncoder: JsonEncoder[AwayProbElo] = JsonEncoder.double
  implicit val awayProbEloDecoder: JsonDecoder[AwayProbElo] = JsonDecoder.double
}

// AwayScores.scala
object AwayScores {
  opaque type AwayScore = Int
  object AwayScore {
    def apply(value: Int): AwayScore = value
    def unapply(awayScore: AwayScore): Int = awayScore
  }
  given CanEqual[AwayScore, AwayScore] = CanEqual.derived

  implicit val awayScoreEncoder: JsonEncoder[AwayScore] = JsonEncoder.int
  implicit val awayScoreDecoder: JsonDecoder[AwayScore] = JsonDecoder.int
}

// AwayTeams.scala
object AwayTeams {
  opaque type AwayTeam = String

  object AwayTeam {
    def apply(value: String): AwayTeam = value
    def unapply(awayTeam: AwayTeam): String = awayTeam
  }
  given CanEqual[AwayTeam, AwayTeam] = CanEqual.derived

  implicit val awayTeamEncoder: JsonEncoder[AwayTeam] = JsonEncoder.string
  implicit val awayTeamDecoder: JsonDecoder[AwayTeam] = JsonDecoder.string
}

// GameDates.scala
object GameDates {
  opaque type GameDate = LocalDate

  object GameDate {
    def apply(value: LocalDate): GameDate = value
    def unapply(gameDate: GameDate): LocalDate = gameDate
  }
  given CanEqual[GameDate, GameDate] = CanEqual.derived

  implicit val gameDateEncoder: JsonEncoder[GameDate] = JsonEncoder.localDate
  implicit val gameDateDecoder: JsonDecoder[GameDate] = JsonDecoder.localDate
}

// HomeElos.scala
object HomeElos {
  opaque type HomeElo = Double
  object HomeElo {
    def apply(value: Double): HomeElo = value
    def unapply(homeElo: HomeElo): Double = homeElo
  }
  given CanEqual[HomeElo, HomeElo] = CanEqual.derived

  implicit val homeEloEncoder: JsonEncoder[HomeElo] = JsonEncoder.double
  implicit val homeEloDecoder: JsonDecoder[HomeElo] = JsonDecoder.double
}

// HomeProbElos.scala
object HomeProbElos {
  opaque type HomeProbElo = Double
  object HomeProbElo {
    def apply(value: Double): HomeProbElo = value
    def unapply(homeProbElo: HomeProbElo): Double = homeProbElo
  }

  given CanEqual[HomeProbElo, HomeProbElo] = CanEqual.derived

  implicit val homeProbEloEncoder: JsonEncoder[HomeProbElo] = JsonEncoder.double
  implicit val homeProbEloDecoder: JsonDecoder[HomeProbElo] = JsonDecoder.double
}

// HomeScores.scala
object HomeScores {
  opaque type HomeScore = Int
  object HomeScore {
    def apply(value: Int): HomeScore = value
    def unapply(homeScore: HomeScore): Int = homeScore
  }
  given CanEqual[HomeScore, HomeScore] = CanEqual.derived

  implicit val homeScoreEncoder: JsonEncoder[HomeScore] = JsonEncoder.int
  implicit val homeScoreDecoder: JsonDecoder[HomeScore] = JsonDecoder.int
}

// HomeTeams.scala
object HomeTeams {
  opaque type HomeTeam = String

  object HomeTeam {
    def apply(value: String): HomeTeam = value
    def unapply(homeTeam: HomeTeam): String = homeTeam
  }
  given CanEqual[HomeTeam, HomeTeam] = CanEqual.derived

  implicit val homeTeamEncoder: JsonEncoder[HomeTeam] = JsonEncoder.string
  implicit val homeTeamDecoder: JsonDecoder[HomeTeam] = JsonDecoder.string
}

// SeasonYears.scala
object SeasonYears {
  opaque type SeasonYear = Int
  object SeasonYear {
    def apply(value: Int): SeasonYear = value
    def unapply(seasonYear: SeasonYear): Int = seasonYear
  }
  given CanEqual[SeasonYear, SeasonYear] = CanEqual.derived

  implicit val seasonYearEncoder: JsonEncoder[SeasonYear] = JsonEncoder.int
  implicit val seasonYearDecoder: JsonDecoder[SeasonYear] = JsonDecoder.int
}
