package plantuml

import java.nio.file.Files
import java.nio.file.FileVisitor
import java.nio.file.Path

import scala.jdk.CollectionConverters._
import net.sourceforge.plantuml.SourceFileReader
import java.io.File
import sbt.io.IO
import scala.collection.mutable.Buffer
import net.sourceforge.plantuml.GeneratedImage
import net.sourceforge.plantuml.FileFormatOption
import net.sourceforge.plantuml.FileFormat

object PlantUmlWalker {
  def walk(input: Path, output: Path): List[(File, File)] =
    Files
      .walk(input)
      .iterator()
      .asScala
      .filter(_.getFileName.toString.endsWith(".puml"))
      .toList
      .map(in =>
        (in.toFile(), output.resolve(input.relativize(in.getParent())).toFile())
      )

  def genImages(
      input: File,
      outputFolder: File,
      formats: FileFormat*
  ): List[GeneratedImage] = {
    if (!outputFolder.exists())
      IO.createDirectory(outputFolder)
    formats.toList.flatMap { format =>
      val plant =
        new SourceFileReader(input, outputFolder, new FileFormatOption(format))
      plant.getGeneratedImages.asScala.toList
    }
  }

  def genAllImages(input: Path, output: Path): List[File] =
    for {
      (in, out) <- walk(input, output)
      gen <- genImages(in, out, FileFormat.SVG)
    } yield gen.getPngFile()
}
