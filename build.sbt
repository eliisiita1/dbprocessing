ThisBuild / version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "dbProcessing"
  )

libraryDependencies ++=  Seq(
  "org.apache.spark"%% "spark-core" % "3.2.4",
  "org.apache.spark" %% "spark-sql" % "3.2.4",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)
