name := """scala_play_sample"""

scalaVersion := "2.10.4"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

EclipseKeys.createSrc := EclipseCreateSrc.All

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.reactivemongo" % "play2-reactivemongo_2.10" % "0.10.5.0.akka23"
)


fork in run := true