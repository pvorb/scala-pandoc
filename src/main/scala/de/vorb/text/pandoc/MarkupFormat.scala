package de.vorb.text.pandoc

sealed trait MarkupFormat {
  def name: String
  override def toString: String = name
}

trait InputFormat extends MarkupFormat
trait OutputFormat extends MarkupFormat

object MarkupFormat {
  // in- and output formats 
  case object Markdown extends InputFormat with OutputFormat {
    val name = "markdown"
  }
  case object LaTeX extends InputFormat with OutputFormat {
    val name = "latex"
  }

  // output formats
  case object HTML extends OutputFormat {
    val name = "html"
  }
  case object PDF extends OutputFormat {
    val name = "pdf"
  }
  case object EPUB extends OutputFormat {
    val name = "epub"
  }
  case object RTF extends OutputFormat {
    val name = "rtf"
  }
  case object ASCIIDoc extends OutputFormat {
    val name = "asciidoc"
  }
}