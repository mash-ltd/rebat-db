name := "Rebat DB"

organization := "com.mashltd"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.2"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.12" % "test"
)

initialCommands := "import com.mashltd.rebatdb._"
