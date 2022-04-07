import Dependencies._

ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

val catsVersion = "2.3.0"
val catsEffectVersion = "3.3.4"
val fs2Version = "3.2.4"

lazy val root = (project in file("."))
  .settings(
    name := "scala-with-cats",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % catsVersion,
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "org.typelevel" %% "cats-effect-testkit" % catsEffectVersion % Test,
      "org.typelevel" %% "cats-effect-testing-specs2" % "1.4.0" % Test,
      "org.typelevel" %% "cats-effect-laws" % catsEffectVersion % Test,
      "co.fs2" %% "fs2-core" % fs2Version,
      "co.fs2" %% "fs2-io" % fs2Version
      //scalaTest % Test
    )
  )

//addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
run / fork := true

addCommandAlias("c", "compile")
addCommandAlias("cc", "~compile")
addCommandAlias("r", "reload")
addCommandAlias("t", "test")
