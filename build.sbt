name := "play2-jsend"

organization := "me.mnedokushev"

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  cache,
  specs2 % Test
)     

scalaSource in Compile := baseDirectory.value / "module"

publishTo := Some("Sonatype Snapshots Nexus" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <scm>
    <url>git@github.com:grouzen/play2-jsend.git</url>
    <connection>scm:git:git@github.com:grouzen/play2-jsend.git</connection>
  </scm>
  <developers>
    <developer>
      <id>grouzen</id>
      <name>Mykhailo Nedokushev</name>
      <url>http://mnedokushev.me/</url>
    </developer>
  </developers>)

licenses := Seq("Apache 2 licence" -> url("https://www.apache.org/licenses/LICENSE-2.0.html"))

homepage := Some(url("https://github.com/grouzen/play2-jsend"))
