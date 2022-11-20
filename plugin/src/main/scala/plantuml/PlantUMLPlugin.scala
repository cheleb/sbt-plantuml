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

  override lazy val projectSettings = Seq(
    plantUMLSource := baseDirectory.value / "src/main/resources/diagram",
    plantUMLTarget := "diagram",
    resourceGenerators.in(Compile)  += Def
      .task[Seq[File]] {
        val inputs = IO.listFiles(plantUMLSource.value).toList

        val output = resourceManaged.in(Compile).value / plantUMLTarget.value

        IO.createDirectory(output)

        inputs
          .flatMap { input =>
            val plant = new SourceFileReader(input, output)
            plant.getGeneratedImages.asScala
          }
          .map(_.getPngFile)
      }
      .taskValue
  )
}
