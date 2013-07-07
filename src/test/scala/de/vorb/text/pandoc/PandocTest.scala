package de.vorb.text.pandoc

import java.io.FileInputStream
import java.nio.file.FileSystems

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object PandocTest extends App {
  val pandocPath = Pandoc(
    from = MarkupFormat.Markdown,
    to = MarkupFormat.HTML,
    in = FileSystems.getDefault().getPath("src/test/resources/guide.txt")
  )

  pandocPath.onSuccess {
    case success => println(success)
  }

  pandocPath.onFailure {
    case err => throw err
  }

  Await.result(pandocPath, 10.seconds)

  val pandocStream = Pandoc(
    from = MarkupFormat.Markdown,
    to = MarkupFormat.HTML,
    in = new FileInputStream("src/test/resources/guide.txt")
  )

  pandocStream.onSuccess {
    case success => println(success)
  }

  pandocStream.onFailure {
    case err => throw err
  }

  Await.result(pandocStream, 10.seconds)
}
