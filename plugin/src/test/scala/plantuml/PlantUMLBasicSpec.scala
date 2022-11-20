package plantuml

import org.scalatest.wordspec.AnyWordSpec
import java.io.File
import java.nio.file.Files

import net.sourceforge.plantuml.SourceFileReader

import scala.collection.JavaConverters._
import org.scalatest.matchers.must.Matchers
import org.scalatest.Assertion

class PlantUMLBasicSpec extends AnyWordSpec with Matchers {

  def listFiles(folder: String) = new File(s"src/test/resources/$folder").listFiles()
  def withTemporaryDirectory(f: File => Assertion) =
    f(Files.createTempDirectory("plantuml-test").toFile())

  "PlantUML" must {
    listFiles("diagram").foreach { file =>
      s"Handle diagram: ${file.getName()}" in {
        withTemporaryDirectory { output =>
          val plantuml = new SourceFileReader(file, output)
          assert(plantuml.getGeneratedImages.asScala
            .map(_.getStatus())
            .forall(_ == 0) == true)
        }
      }
    }
  }
}
