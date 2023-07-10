val scala3Version = "3.3.0"
val zioVersion = "2.0.15"

lazy val root = project
  .in(file("."))
  .settings(
    name := "solver-sudoku",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,


    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,

      // Add other libraries like zio-nio and zip-json here if needed
      "dev.zio" %% "zio-nio" % "2.0.1",
      "dev.zio" %% "zio-json" % "0.6.0",
      "dev.zio" %% "zio-test" % zioVersion,
      "dev.zio" %% "zio-test-sbt" % zioVersion,
   ).map(_ % Compile),
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29"
    ).map(_ % Test)
  )
