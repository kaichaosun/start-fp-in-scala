import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scala-walk-through",
    libraryDependencies ++= projectDependencies,
    scalacOptions ++= advancedScalaOptions
  )

val circeVersion = "0.9.3"
lazy val projectDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.0.3",
  "org.typelevel" %% "cats-core" % "1.0.1",
  "org.atnos"     %% "eff"       % "5.0.0",
  "com.github.nscala-time" %% "nscala-time" % "2.18.0",
  "io.circe"      %% "circe-core" % circeVersion,
  "io.circe"      %% "circe-generic" % circeVersion,
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

lazy val advancedScalaOptions = Seq(
  "-Ypartial-unification"
)