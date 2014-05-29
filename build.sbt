name := "play2-jsend"

organization := "me.mnedokushev"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache
)     

play.Project.playScalaSettings

scalaSource in Compile := baseDirectory.value / "module"
