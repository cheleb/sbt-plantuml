package plantuml

import sbt._
import sbt.Keys._

import net.sourceforge.plantuml.SourceFileReader

import scala.collection.JavaConverters._

object PlantUMLPlugin extends AutoPlugin {
  override def requires: Plugins = plugins.JvmPlugin

  override def trigger: PluginTrigger = allRequirements

  object autoImport {
    val plantUMLSource = settingKey[File]("plantUML sources")
    val plantUMLTarget = settingKey[String]("plantUML target")
  }

  import autoImport._

  override lazy val buildSettings: Seq[Setting[_]] = Seq(
    plantUMLSource := baseDirectory.value / "src/main/plantuml",
    plantUMLTarget := "mdoc"
  )

  override lazy val projectSettings = Seq(
    resourceGenerators.in(Compile) += Def
      .task[Seq[File]] {
        val logger = streams.value.log
        logger.info(s"Generating PlantUML diagrams from ${plantUMLSource.value}")
        val source = plantUMLSource.value.toPath()
        val outputBaseDir = target.value / plantUMLTarget.in(Compile).value
        logger.info(s"Generating PlantUML diagrams to ${outputBaseDir}")
        PlantUmlWalker.genAllPngs(source, outputBaseDir.toPath()).toList
      }
      .taskValue
  )
}
