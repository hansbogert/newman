import org.scalastyle.sbt.ScalastylePlugin
import sbt._
import sbt.Keys._

name := "newman"

organization := "io.megam"

scalaVersion := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-feature")

scalacOptions in Test ++= Seq("-Yrangepos")


libraryDependencies ++= {
  val httpCoreVersion = "4.4.1"
  val httpClientVersion = "4.4.1"
  val scalaCheckVersion = "1.12.2"
  val specs2Version = "3.6"
  val mockitoVersion = "1.9.0"
  val liftJsonVersion = "3.0-M5-1"
  val sprayVersion = "1.3.3"
  val akkaVersion = "2.3.10"


  Seq(
    "org.apache.httpcomponents" % "httpcore" % httpCoreVersion,
    "org.apache.httpcomponents" % "httpclient" % httpClientVersion exclude("org.apache.httpcomponents", "httpcore"),
    "io.spray" %% "spray-client" % sprayVersion,
    "io.spray" %% "spray-caching" % sprayVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.twitter" %% "finagle-http" % "6.25.0" exclude("commons-codec", "commons-codec"),
    "org.scalaz" %% "scalaz-core" % "7.1.2",
    "org.scalaz.stream" %% "scalaz-stream" % "0.7a",
    "net.liftweb" %% "lift-json-scalaz7" % liftJsonVersion,
    "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test",
    "org.specs2" %% "specs2-core" % specs2Version % "test" exclude("org.scalaz", "scalaz-core_2.11"),
    "org.pegdown" % "pegdown" % "1.5.0" % "test" exclude("org.parboiled", "parboiled-core"),
    "org.mockito" % "mockito-all" % mockitoVersion % "test"
  )
}

resolvers += Resolver.bintrayRepo("scalaz", "releases")

testOptions in Test += Tests.Argument("html", "console")

dependencyOverrides <+= (scalaVersion) { vsn => "org.scala-lang" % "scala-library" % vsn }

logBuffered := false

ScalastylePlugin.Settings

lazy val commonSettings = Seq(
  version in ThisBuild := "1.3.8",
  organization in ThisBuild := "Megam Systems"
)

lazy val root = (project in file(".")).
  settings(commonSettings).
  settings(
    sbtPlugin := true,
    name := "newman",
    description := """This is the fork of newman https://github.com/stackmob/newman upgraded to scala 2.11 and scalaz 7.1.2. We primarily use it for testing our API Gateway : https://github.com/megamsys/megam_gateway.git
    Feel free to collaborate at https://github.com/megamsys/newman.git.""",
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    publishMavenStyle := false,
    bintrayOrganization := Some("megamsys"),
    bintrayRepository := "scala"
  )
