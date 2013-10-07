package de.vorb.text.pandoc

import java.io.FileInputStream
import java.nio.file.FileSystems

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.util.Success
import scala.util.Failure

object PandocTest extends App {
  val pandocPath = Pandoc(
    from = MarkupFormat.Markdown,
    to = MarkupFormat.HTML,
    in = FileSystems.getDefault().getPath("src/test/resources/guide.txt")
  )

  pandocPath.onComplete {
    case Success(result) => println(result)
    case Failure(error)  => throw error
  }

  Await.result(pandocPath, 10.seconds)

  val pandocStream = Pandoc(
    from = MarkupFormat.Markdown,
    to = MarkupFormat.HTML,
    in = new FileInputStream("src/test/resources/guide.txt")
  )

  pandocStream.onComplete {
    case Success(result) => println(result)
    case Failure(error)  => throw error
  }

  Await.result(pandocStream, 10.seconds)

  val pandocRaw = Pandoc(
    from = MarkupFormat.Markdown,
    to = MarkupFormat.HTML,
    out = FileSystems.getDefault().getPath("src/test/resources/guide.html"),
    in = FileSystems.getDefault().getPath("src/test/resources/guide.txt"))

  pandocRaw.onComplete {
    case Success(())     => println("success")
    case Failure(error) => throw error;
  }
  
  Await.result(pandocRaw, 10.seconds)
}
