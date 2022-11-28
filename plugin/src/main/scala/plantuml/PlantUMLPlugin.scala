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
    val plantUMLTarget = settingKey[File]("plantUML target")
  }

  import autoImport._

  override lazy val buildSettings: Seq[Setting[_]] = Seq(
    plantUMLSource := baseDirectory.value / "src/main/plantuml",
    plantUMLTarget := resourceManaged.in(Compile).value / "diagram"
  )

  override lazy val projectSettings = Seq(
    resourceGenerators.in(Compile) += Def
      .task[Seq[File]] {
        val source = plantUMLSource.value.toPath()
        val outputBaseDir = plantUMLTarget.value.toPath()
        PlantUmlWalker.genAllPngs(source, outputBaseDir).toList
      }
      .taskValue
  )
}
