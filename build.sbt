import atd.sbtthrift.ThriftPlugin

seq(ThriftPlugin.thriftSettings: _*)

name := "Rebat DB"

organization := "com.mashltd"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.2"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.12" % "test",
  "org.squeryl" %% "squeryl" % "0.9.5-2",
  "mysql" % "mysql-connector-java" % "5.1.+",
  "org.yaml" % "snakeyaml" % "1.8",
  "org.apache.thrift" % "libthrift" % "0.9.0",
  "org.slf4j" % "slf4j-log4j12" % "1.6.6"
)

initialCommands := "import com.mashltd.rebatdb._"
