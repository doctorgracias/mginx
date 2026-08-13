ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.3"

val zioVersion    = "2.1.9"
val tapirVersion  = "1.11.7"

lazy val root = (project in file("."))
  .settings(
    name := "zio-tapir-demo",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio"              % zioVersion,
      "dev.zio" %% "zio-http"         % "3.0.1",
      "com.softwaremill.sttp.tapir" %% "tapir-zio"             % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-zio" % tapirVersion,
      "dev.zio" %% "zio-json"        % "0.7.3"
    )
  )