import atd.sbtthrift.ThriftPlugin

seq(ThriftPlugin.thriftSettings: _*)

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

seq(SbtJswPlugin.jswPluginSettings :_*)

name := "rebat-db"

organization := "com.mashltd"

version := "0.1.1"

scalaVersion := "2.9.2"

mainClass in oneJar := Some("com.mashltd.rebatdb.Rebat")

jswMainClass in Dist := "com.mashltd.rebatdb.Rebat"

configSourceDirs in Dist <<= baseDirectory map { base => Seq(base / "conf")}

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.12" % "test",
  "org.squeryl" %% "squeryl" % "0.9.5-2",
  "mysql" % "mysql-connector-java" % "5.1.+",
  "org.yaml" % "snakeyaml" % "1.8",
  "org.apache.thrift" % "libthrift" % "0.9.0",
  "ch.qos.logback" % "logback-classic" % "1.0.9"
)

initialCommands := "import com.mashltd.rebatdb._"
