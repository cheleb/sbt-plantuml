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

object PlantUmlWalker {
  def walk(input: Path, output: Path): List[(File, File)] =
    Files
      .walk(input)
      .iterator()
      .asScala
      .filter(_.getFileName.toString.endsWith(".puml"))
      .toList
      .map(in => (in.toFile(), output.resolve(input.relativize(in.getParent())).toFile()))

  def genPng(input: File, output: File): List[GeneratedImage] = {
    IO.createDirectory(output)
    val plant = new SourceFileReader(input, output)
    plant.getGeneratedImages.asScala.toList
  }

  def genAllPngs(input: Path, output: Path): List[File] =
    for {
      (in, out) <- walk(input, output)
      gen <- genPng(in, out)
    } yield gen.getPngFile()
}
