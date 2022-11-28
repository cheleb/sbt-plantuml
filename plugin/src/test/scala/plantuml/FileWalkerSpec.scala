package plantuml

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import java.nio.file.Paths

class FileWalkerSpec extends AnyWordSpec with Matchers {

  "File walker" must {
    "browse a directory" in {
      val files = PlantUmlWalker
        .walk(Paths.get("src", "test", "resources", "diagram"), Paths.get("docs/diagram"))
        .map { case (input, output) =>
          (input.toString, output.toString)
        }
      files must contain theSameElementsAs List(
        ("src/test/resources/diagram/activity.puml", "docs/diagram"),
        ("src/test/resources/diagram/basic.puml", "docs/diagram"),
        ("src/test/resources/diagram/gantt.puml", "docs/diagram"),
        ("src/test/resources/diagram/sub/sequence.puml", "docs/diagram/sub")
      )
    }

  }
}
