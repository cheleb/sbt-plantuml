package plantuml

import sbt.*
import sbt.Keys.*

import net.sourceforge.plantuml.SourceFileReader

import scala.jdk.CollectionConverters._
import java.nio.file.Files
import net.sourceforge.plantuml.FileFormat

object PlantUMLPlugin extends AutoPlugin {

  object Formats {
    val PNG = FileFormat.PNG
    val SVG = FileFormat.SVG
    val EPS = FileFormat.EPS
    val PDF = FileFormat.PDF
    val VDX = FileFormat.VDX
    val XMI_STANDARD = FileFormat.XMI_STANDARD
    val XMI_ARGO = FileFormat.XMI_ARGO
    val SCXML = FileFormat.SCXML
    val HTML5 = FileFormat.HTML5
  }
  override def requires: Plugins = plugins.JvmPlugin

  override def trigger: PluginTrigger = noTrigger

  object autoImport {
    val plantUMLSource = settingKey[File]("plantUML sources")
    val plantUMLTarget =
      settingKey[String]("plantUML target").withRank(KeyRanks.Invisible)
    val plantUMLFormats = settingKey[Seq[FileFormat]]("plantUML formats")
      .withRank(KeyRanks.Invisible)
  }

  import autoImport._

  override lazy val buildSettings: Seq[Setting[?]] = Seq(
    plantUMLSource := baseDirectory.value / "src/main/plantuml",
    plantUMLTarget := "mdoc",
    plantUMLFormats := Seq(FileFormat.PNG)
  )

  override lazy val projectSettings = Seq(
    (Compile / resourceGenerators) += Def
      .task[Seq[File]] {
        val logger = streams.value.log
        logger.info(
          s"Generating PlantUML diagrams from ${plantUMLSource.value}"
        )
        val source = plantUMLSource.value.toPath()
        if (Files.exists(source)) {
          val outputBaseDir = target.value / (Compile / plantUMLTarget).value
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
