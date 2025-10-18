//Customized release
//import sbtrelease.ReleaseStateTransformations._

val scala213 = "2.13.10"
val scala212 = "2.12.17"
val scala32 = "3.2.2"
val mainScala = scala212
val allScala = Seq(scala32, scala213, scala212)

inThisBuild(
  List(
    organization := "dev.cheleb",
    homepage := Some(url("https://github.com/cheleb/plantuml-sbt-plugin")),
    licenses := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    useCoursier := false,
    scalaVersion := mainScala,
//    crossScalaVersions := allScala,
    sbtPluginPublishLegacyMavenStyle := false,
    Test / parallelExecution := false,
    Test / fork := true,
    run / fork := true,
    publishTo := {
      val centralSnapshots =
        "https://central.sonatype.com/repository/maven-snapshots/"
      if (isSnapshot.value) Some("central-snapshots" at centralSnapshots)
      else localStaging.value
    },
    versionScheme := Some("early-semver"),
    pgpPublicRing := file("/tmp/public.asc"),
    pgpSecretRing := file("/tmp/secret.asc"),
    pgpPassphrase := sys.env.get("PGP_PASSWORD").map(_.toArray),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/cheleb/sbt-plantuml/"),
        "scm:git:git@github.com:cheleb/sbt-plantuml.git"
      )
    ),
    developers := List(
      Developer(
        "cheleb",
        "Olivier NOUGUIER",
        "olivier.nouguier@gmail.com",
        url("https://github.com/cheleb")
      )
    )
  )
)

name := "sbt-plantuml"

scalaVersion := mainScala

onLoadMessage := s"Welcome to sbt-plantuml ${version.value}"

publish / skip := true // don't publish the root project

lazy val plugin = project
  .in(file("plugin"))
  .enablePlugins(SbtPlugin)
  .settings(
    moduleName := "sbt-plantuml",
    libraryDependencies += "net.sourceforge.plantuml" % "plantuml" % "1.2025.9",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    pluginCrossBuild / sbtVersion := "1.11.1"
  )
