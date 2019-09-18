import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.3",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "scala-walk-through",
    libraryDependencies ++= projectDependencies,
    scalacOptions ++= advancedScalaOptions
  )

val CirceVersion = "0.10.0"
val Specs2Version = "4.0.2"
val Http4sVersion = "0.20.0"
val AkkaVersion = "2.5.6"
val CatsVersion = "1.0.1"
val doobieVersion = "0.6.0"
val monocleVersion = "1.5.0"
val effVersion = "5.2.0"

lazy val projectDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.0.3",
  "org.typelevel" %% "cats-core" % CatsVersion,
  "org.typelevel" %% "cats-free" % CatsVersion,
  "org.atnos" %% "eff" % effVersion,
  "org.atnos" %% "eff-monix" % effVersion,
  "org.atnos" %% "eff-cats-effect" % effVersion,
  "com.github.nscala-time" %% "nscala-time" % "2.18.0",
  "io.circe" %% "circe-core" % CirceVersion,
  "io.circe" %% "circe-parser" % CirceVersion,
  "io.circe" %% "circe-generic" % CirceVersion,
  "co.fs2" %% "fs2-core" % "0.10.1", // For cats 1.0.1 and cats-effect 0.8
  "org.specs2" %% "specs2-core" % Specs2Version % Test,
  "org.specs2" %% "specs2-scalacheck" % Specs2Version % Test,
  "io.monix" %% "monix" % "3.0.0",
  "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "org.typelevel" %% "cats-effect" % "1.0.0",
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % "2.5.16",
  "com.typesafe.akka" %% "akka-remote" % "2.5.18",
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2" % doobieVersion,
  "com.chuusai" %% "shapeless" % "2.3.3",
  "org.scalaz" %% "scalaz-core" % "7.2.27",
  "io.circe" %% "circe-yaml" % "0.9.0",
  "com.github.julien-truffaut" %% "monocle-core" % monocleVersion,
  "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion,
  "com.github.julien-truffaut" %% "monocle-law" % monocleVersion % "test",
  "com.github.tototoshi" %% "scala-csv" % "1.3.5",
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

lazy val advancedScalaOptions = Seq(
  "-Ypartial-unification"
)
