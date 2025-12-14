package plantuml

import org.scalatest.wordspec.AnyWordSpec
import java.io.File
import java.nio.file.Files

import net.sourceforge.plantuml.SourceFileReader

import scala.collection.JavaConverters._
import org.scalatest.matchers.must.Matchers
import org.scalatest.Assertion
import java.nio.file.Paths
import net.sourceforge.plantuml.FileFormat

class PlantUMLBasicSpec extends AnyWordSpec with Matchers {

  def listFiles(folder: String) =
    PlantUmlWalker.walk(
      Paths.get("src", "test", "resources", folder),
      Files.createTempDirectory("plantuml-test")
    )

  "PlantUML" must {
    listFiles("diagram").foreach { case (in, out) =>
      s"Handle diagram: ${in.toString()}" in {
        assert(
          PlantUmlWalker
            .genImages(in, out, FileFormat.SVG, FileFormat.PNG)
            .map { gen =>
              gen.getStatus()
            }
            .forall(_ == 0) == true
        )
      }
    }
  }
}
