package de.vorb.text.pandoc

import java.io.{ BufferedReader, BufferedWriter, InputStream, InputStreamReader, OutputStreamWriter }
import java.lang.{ Process, ProcessBuilder }
import java.nio.file.Path
import java.util.ArrayList

import scala.annotation.implicitNotFound
import scala.concurrent.{ ExecutionContext, Future, future, blocking }

/**
 * Wraps the Pandoc executable.
 */
object Pandoc {

  /**
   * Call Pandoc with the arguments specified in `args`.
   *
   * E.g. for `Pandoc(List("-f", "html", "-t", "markdown"))`
   * `pandoc -f html -t markdown` will be run.
   *
   * @param args sequence of arguments for the command line. If an output file
   *             is specified, execution will fail.
   * @param ctx  execution context
   *
   * @return resulting document as a `Future[String]`
   */
  def apply(args: Seq[String])(implicit ctx: ExecutionContext): Future[String] =
    readOutputAsString(new ProcessBuilder(commandFor(args)).start())

  /**
   * Call Pandoc with the given input format, output format, output and source
   * file path.
   *
   * @param from input format
   * @param to   output format
   * @param out  path to output file
   * @param in   path to source file
   * @param additionalArguments additional parameters
   * @param ctx  execution context
   *
   * @return `Future[Unit]` for listening on completion
   */
  def apply(from: InputFormat, to: OutputFormat, out: Path, in: Path,
    additionalArguments: String*)(
      implicit ctx: ExecutionContext): Future[Unit] = {
    val args = "-f" :: from.toString :: "-t" :: to.toString :: "-o" ::
      out.toString :: in.toString :: additionalArguments.toList
    val process = new ProcessBuilder(commandFor(args)).start()

    future {
      process.waitFor()
    }
  }

  /**
   * Call Pandoc with the given input format, output format and source file
   * path.
   *
   * @param from input format
   * @param to   output format
   * @param in   path to source file
   * @param additionalArguments additional parameters
   * @param ctx  execution context
   *
   * @return resulting document as a `Future[String]`
   */
  def apply(from: InputFormat, to: OutputFormat, in: Path,
    additionalArguments: String*)(
      implicit ctx: ExecutionContext): Future[String] =
    apply("-f" :: from.toString :: "-t" :: to.toString :: in.toString ::
      additionalArguments.toList)

  /**
   * Call Pandoc with the given input format, output format and an input stream.
   *
   * @param from input format
   * @param to   output format
   * @param in   input stream
   * @param additionalArguments additional parameters
   * @param ctx  execution context
   *
   * @return resulting document as a `Future[String]`
   */
  def apply(from: InputFormat, to: OutputFormat, in: InputStream,
    additionalArguments: String*)(
      implicit ctx: ExecutionContext): Future[String] =
    apply(from, to, new InputStreamReader(in, "UTF-8"))(ctx)

  /**
   * Call Pandoc with the given input format, output format and an input stream
   * reader.
   *
   * @param from input format
   * @param to   output format
   * @param in   input stream reader
   * @param additionalArguments additional parameters
   * @param ctx  execution context
   *
   * @return resulting document as a `Future[String]`
   */
  def apply(from: InputFormat, to: OutputFormat, in: InputStreamReader,
    additionalArguments: String*)(
      implicit ctx: ExecutionContext): Future[String] = {

    val cmd = commandFor(from, to)
    additionalArguments.foreach(cmd.add(_)) // add additional parameters

    val process = new ProcessBuilder(cmd).start()

    // future that writes the characters from the input stream to the output
    // stream
    future {
      val reader = new BufferedReader(in)
      val writer = new BufferedWriter(new OutputStreamWriter(
        process.getOutputStream(), "UTF-8"))

      blocking {
        var c = reader.read()
        while (c != -1) {
          writer.write(c)
          c = reader.read()
        }
        writer.flush()

        reader.close()
        writer.close()
      }
    }

    readOutputAsString(process)
  }

  private def readOutputAsString(p: Process, encoding: String = "UTF-8")(
    implicit ctx: ExecutionContext): Future[String] = {
    val sb = new StringBuilder

    future {
      val in = new BufferedReader(new InputStreamReader(
        p.getInputStream(), encoding))

      blocking {
        var c = in.read()
        while (c != -1) {
          if (c != '\r') // filter windows style line endings
            sb += c.toChar
          c = in.read()
        }
      }

      sb.result
    }
  }

  private def commandFor(args: Seq[String]) = {
    val cmd = new ArrayList[String](args.length + 1)
    cmd.add("pandoc")
    args.foreach(cmd.add(_))
    cmd
  }

  private def commandFor(from: InputFormat, to: OutputFormat) = {
    val cmd = new ArrayList[String](5)
    cmd.add("pandoc")
    cmd.add("-f")
    cmd.add(from.toString)
    cmd.add("-t")
    cmd.add(to.toString)
    cmd
  }
}