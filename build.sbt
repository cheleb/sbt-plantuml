//Customized release
//import sbtrelease.ReleaseStateTransformations._

val scala212 = "2.12.21"
val scala3 = "3.9.0"

inThisBuild(
  List(
    organization := "dev.cheleb",
    homepage := Some(uri("https://github.com/cheleb/sbt-plantuml")),
    licenses := List(
      "Apache-2.0" -> uri("http://www.apache.org/licenses/LICENSE-2.0")
    ),
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
        uri("https://github.com/cheleb/sbt-plantuml/"),
        "scm:git:git@github.com:cheleb/sbt-plantuml.git"
      )
    ),
    developers := List(
      Developer(
        "cheleb",
        "Olivier NOUGUIER",
        "olivier.nouguier@gmail.com",
        uri("https://github.com/cheleb")
      )
    )
  )
)


onLoadMessage := s"Welcome to sbt-plantuml ${version.value}"


lazy val plugin = project
  .in(file("plugin"))
  .enablePlugins(SbtPlugin)
  .settings(
    moduleName := "sbt-plantuml",
    libraryDependencies += "net.sourceforge.plantuml" % "plantuml" % "1.2026.7",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.20" % Test,
    (pluginCrossBuild / sbtVersion) := {
      scalaBinaryVersion.value match {
        case "2.12" => "1.12.0"
        case _      => "2.0.8"
      }
    },
    scriptedSbt := {
      scalaBinaryVersion.value match {
        case "2.12" => "1.12.0"
        case _      => (pluginCrossBuild / sbtVersion).value
      }
    }
  )

lazy val root = project.
  settings(
    name := "root",
    publish / skip := true
  ).
  aggregate(plugin)