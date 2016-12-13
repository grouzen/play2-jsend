name := "play2-jsend"

organization := "me.mnedokushev"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  cache,
  specs2 % Test
)     

scalaSource in Compile := baseDirectory.value / "module"
