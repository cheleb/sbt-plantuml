//Customized release
//import sbtrelease.ReleaseStateTransformations._

lazy val scala212 = "2.12.15"
lazy val scala3 = "3.3.4"

val mainScala = scala212

inThisBuild(
  List(
    organization := "dev.cheleb",
    homepage := Some(url("https://github.com/cheleb/plantuml-sbt-plugin")),
    licenses := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    crossScalaVersions := Seq(scala212, scala3),
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
    libraryDependencies += "net.sourceforge.plantuml" % "plantuml" % "1.2025.4",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    pluginCrossBuild / sbtVersion := "1.0.4",
    addSbtPlugin("com.dwijnand" % "sbt-dynver" % "4.1.1"),
    addSbtPlugin("com.github.sbt" % "sbt-git" % "2.0.1"),
    addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.11.3"),
    addSbtPlugin("com.github.sbt" % "sbt-pgp" % "2.2.1")
  )
