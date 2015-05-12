import org.scalastyle.sbt.ScalastylePlugin
import NewmanReleaseSteps._
import sbtrelease._
import ReleaseStateTransformations._
import ReleasePlugin._
import ReleaseKeys._
import sbt._

name := "newman"

organization := "com.github.indykish"

scalaVersion := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-feature")

scalacOptions in Test ++= Seq("-Yrangepos")


libraryDependencies ++= {
  val httpCoreVersion = "4.2.5"
  val httpClientVersion = "4.2.5"
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

//conflictManager := ConflictManager.strict

dependencyOverrides <+= (scalaVersion) { vsn => "org.scala-lang" % "scala-library" % vsn }

logBuffered := false

ScalastylePlugin.Settings

releaseSettings

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  setReadmeReleaseVersion,
  tagRelease,
  publishArtifacts.copy(action = publishSignedAction),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

publishTo <<= (version) { version: String =>
  val nexus = "https://oss.sonatype.org/"
  if (version.trim.endsWith("SNAPSHOT")) {
    Some("snapshots" at nexus + "content/repositories/snapshots")
   } else {
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
}

publishMavenStyle := true

publishArtifact in Test := true

pomExtra := (
  <url>https://github.com/stackmob/newman</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:stackmob/newman.git</url>
    <connection>scm:git:git@github.com:stackmob/newman.git</connection>
  </scm>
  <developers>
    <developer>
      <id>arschles</id>
      <name>Aaron Schlesinger</name>
      <url>http://www.stackmob.com</url>
    </developer>
    <developer>
      <id>devmage</id>
      <name>Andrew Harris</name>
      <url>http://www.stackmob.com</url>
    </developer>
    <developer>
      <id>taylorleese</id>
      <name>Taylor Leese</name>
      <url>http://www.stackmob.com</url>
    </developer>
    <developer>
      <id>kelseyq</id>
      <name>Kelsey Innis</name>
      <url>http://www.stackmob.com</url>
    </developer>
    <developer>
      <id>wpalmeri</id>
      <name>Will Palmeri</name>
      <url>http://www.stackmob.com</url>
    </developer>
    <developer>
      <id>jrwest</id>
      <name>Jordan West</name>
      <url>http://github.com/jrwest</url>
    </developer>
  </developers>
)
