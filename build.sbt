name := "Scarbound"

version := "1.0"

organization := "com.fractaloop"

version := "0.0-SNAPSHOT"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-optimize")

scalacOptions in (Compile, doc) ++= Seq("-diagrams","-implicits")

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.6",
  "ch.qos.logback" % "logback-classic" % "1.1.1" % "runtime",
  "com.github.scopt" %% "scopt" % "3.2.0",
  "com.typesafe" % "config" % "1.2.0",
  "io.netty" % "netty" % "3.9.0.Final"
)

