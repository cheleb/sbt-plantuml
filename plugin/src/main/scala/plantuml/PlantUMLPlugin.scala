package plantuml

import sbt._
import sbt.Keys._

import net.sourceforge.plantuml.SourceFileReader

import scala.collection.JavaConverters._
import java.nio.file.Files
import net.sourceforge.plantuml.FileFormat

object PlantUMLPlugin extends AutoPlugin {
  override def requires: Plugins = plugins.JvmPlugin

  override def trigger: PluginTrigger = noTrigger

  object autoImport {
    val plantUMLSource = settingKey[File]("plantUML sources")
    val plantUMLTarget = settingKey[String]("plantUML target")
    val plantUMLFormats = settingKey[Seq[FileFormat]]("plantUML formats")
  }

  import autoImport._

  override lazy val buildSettings: Seq[Setting[_]] = Seq(
    plantUMLSource := baseDirectory.value / "src/main/plantuml",
    plantUMLTarget := "mdoc",
    plantUMLFormats := Seq(FileFormat.PNG)
  )

  override lazy val projectSettings = Seq(
    resourceGenerators.in(Compile) += Def
      .task[Seq[File]] {
        val logger = streams.value.log
        logger.info(
          s"Generating PlantUML diagrams from ${plantUMLSource.value}"
        )
        val source = plantUMLSource.value.toPath()
        if (Files.exists(source)) {
          val outputBaseDir = target.value / plantUMLTarget.in(Compile).value
          logger.info(s"Generating PlantUML diagrams to ${outputBaseDir}")
          PlantUmlWalker.genAllImages(source, outputBaseDir.toPath()).toList
        } else {
          logger.info(s"PlantUML source directory ${source} does not exist")
          List.empty
        }
      }
      .taskValue
  )
}
