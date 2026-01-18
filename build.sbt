//Customized release
//import sbtrelease.ReleaseStateTransformations._

val scala212 = "2.12.21"
val scala3 = "3.7.4"

inThisBuild(
  List(
    organization := "dev.cheleb",
    homepage := Some(url("https://github.com/cheleb/plantuml-sbt-plugin")),
    licenses := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    useCoursier := false,
    crossScalaVersions := Seq(scala212, scala3),
    scalaVersion := scala212,
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

onLoadMessage := s"Welcome to sbt-plantuml ${version.value}"

publish / skip := true // don't publish the root project

lazy val plugin = project
  .in(file("plugin"))
  .enablePlugins(SbtPlugin)
  .settings(
    moduleName := "sbt-plantuml",
    libraryDependencies += "net.sourceforge.plantuml" % "plantuml" % "1.2026.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    (pluginCrossBuild / sbtVersion) := {
      scalaBinaryVersion.value match {
        case "2.12" => "1.12.0"
        case _      => "2.0.0-RC8"
      }
    },
    scriptedSbt := {
      scalaBinaryVersion.value match {
        case "2.12" => "1.12.0"
        case _      => (pluginCrossBuild / sbtVersion).value
      }
    }
  )
