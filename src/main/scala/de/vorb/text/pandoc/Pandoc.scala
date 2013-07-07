package de.vorb.text.pandoc

import java.io.{ BufferedReader, BufferedWriter, InputStream, InputStreamReader, OutputStreamWriter }
import java.lang.{ Process, ProcessBuilder }
import java.nio.file.Path
import java.util.ArrayList

import scala.annotation.implicitNotFound
import scala.concurrent.{ ExecutionContext, Future, future }

object Pandoc {
  def apply(args: Seq[String])(implicit ctx: ExecutionContext): Future[String] =
    readOutputAsString(new ProcessBuilder(command(args)).start())

  def apply(from: InputFormat = MarkupFormat.Markdown,
    to: OutputFormat = MarkupFormat.HTML,
    in: Path)(implicit ctx: ExecutionContext): Future[String] =
    apply("-f" :: from.name :: "-t" :: to.name :: in.toString :: Nil)

  def apply(from: InputFormat,
    to: OutputFormat,
    in: InputStream)(implicit ctx: ExecutionContext): Future[String] = {

    val process = new ProcessBuilder(command(from, to)).start()

    future {
      val reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))
      val writer = new BufferedWriter(new OutputStreamWriter(
        process.getOutputStream(), "UTF-8"))

      var c = reader.read()
      while (c != -1) {
        writer.write(c)
        c = reader.read()
      }
      writer.flush()

      reader.close()
      writer.close()
    }

    readOutputAsString(process)
  }

  private def readOutputAsString(p: Process, encoding: String = "UTF-8")(
    implicit ctx: ExecutionContext): Future[String] = {
    val sb = new StringBuilder

    future {
      val in = new BufferedReader(new InputStreamReader(
        p.getInputStream(), encoding))

      var c = in.read()
      while (c != -1) {
        if (c != '\r') // filter windows style line endings
          sb += c.toChar
        c = in.read()
      }

      sb.result
    }
  }

  private def command(args: Seq[String]) = {
    val cmd = new ArrayList[String](args.length + 1)
    cmd.add("pandoc")
    args.foreach(cmd.add(_))
    cmd
  }

  private def command(from: InputFormat, to: OutputFormat) = {
    val cmd = new ArrayList[String](5)
    cmd.add("pandoc")
    cmd.add("-f")
    cmd.add(from.name)
    cmd.add("-t")
    cmd.add(to.name)
    cmd
  }
}