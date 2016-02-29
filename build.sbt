name := "newman"

organization := "io.megam"

description := """This is the fork of newman https://github.com/stackmob/newman upgraded to scala 2.11 and scalaz 7.1.2. We primarily use it for testing our API Gateway : https://github.com/megamsys/megam_gateway.git
Feel free to collaborate at https://github.com/megamsys/newman.git."""

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayOrganization := Some("hbogert")

bintrayRepository := "maven"

publishMavenStyle := true

scalaVersion := "2.11.7"

scalacOptions in Test ++= Seq("-Yrangepos")

scalacOptions := Seq(
  "-target:jvm-1.7",
  "-deprecation",
  "-feature",
  "-optimise",
  "-Xcheckinit",
  "-Xlint",
  "-Xverify",
  "-Yinline",
  "-Yclosure-elim",
  "-Yconst-opt",
  "-Ybackend:GenBCode",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:reflectiveCalls",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-Ydead-code")

  incOptions := incOptions.value.withNameHashing(true)

  resolvers ++= Seq(Resolver.sonatypeRepo("releases"), Resolver.sonatypeRepo("snapshots"),
  Resolver.bintrayRepo("scalaz", "releases")
)


libraryDependencies ++= {
  val httpCoreVersion = "4.4.4"
  val httpClientVersion = "4.5.1"
  val scalaCheckVersion = "1.12.2"
  val specs2Version = "3.6.5-20151108070227-1e34889"
  val liftJsonVersion = "3.0-M6"
  val sprayVersion = "1.3.3"
  val akkaVersion = "2.4.0"

  Seq(
    "org.apache.httpcomponents" % "httpcore" % httpCoreVersion,
    "org.apache.httpcomponents" % "httpclient" % httpClientVersion exclude("org.apache.httpcomponents", "httpcore"),
    "io.spray" %% "spray-client" % sprayVersion,
    "io.spray" %% "spray-caching" % sprayVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.twitter" %% "finagle-http" % "6.28.0" exclude("commons-codec", "commons-codec"),
    "org.scalaz" %% "scalaz-core" % "7.1.5",
    "org.scalaz.stream" %% "scalaz-stream" % "0.8",
    "net.liftweb" %% "lift-json-scalaz7" % liftJsonVersion,
    "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test",
    "org.specs2" %% "specs2-core" % specs2Version % "test" exclude("org.scalaz", "scalaz-core_2.11"),
    "org.mockito" % "mockito-all" % "1.10.19" % "test"
  )
}
