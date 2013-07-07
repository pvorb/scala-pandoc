package de.vorb.text.pandoc

/**
 * Super class of the different markup formats that are supported by Pandoc.
 */
sealed trait MarkupFormat {
  /**
   * Name of the markup format.
   */
  protected def name: String

  /**
   * @return name of the markup format
   */
  override def toString: String = name
}

/**
 * Input format that can be read by Pandoc.
 */
trait InputFormat extends MarkupFormat

/**
 * Output format that can be written by Pandoc.
 */
trait OutputFormat extends MarkupFormat

/**
 * Concrete markup formats.
 */
object MarkupFormat {
  /**
   * Native Haskell.
   */
  case object Native extends InputFormat with OutputFormat {
    val name = "native"
  }

  /**
   * JSON version of native AST.
   */
  case object JSON extends InputFormat with OutputFormat {
    val name = "json"
  }

  /**
   * Plain text.
   */
  case object PlainText extends OutputFormat {
    val name = "plain"
  }

  /**
   * Pandoc's extended Markdown.
   */
  case object Markdown extends InputFormat with OutputFormat {
    val name = "markdown"
  }

  /**
   * Original unextended Markdown.
   */
  case object MarkdownStrict extends InputFormat with OutputFormat {
    val name = "markdown_strict"
  }

  /**
   * PHP Markdown Extra extended Markdown.
   */
  case object MarkdownPHPExtra extends InputFormat with OutputFormat {
    val name = "markdown_phpextra"
  }

  /**
   * GitHub flavored Markdown.
   */
  case object MarkdownGitHub extends InputFormat with OutputFormat {
    val name = "markdown_github"
  }

  /**
   * Textile.
   */
  case object Textile extends InputFormat {
    val name = "textile"
  }

  /**
   * ReStructuredText.
   */
  case object RST extends InputFormat with OutputFormat {
    val name = "rst"
  }

  /**
   * (X)HTML.
   */
  case object HTML extends InputFormat with OutputFormat {
    val name = "html"
  }

  /**
   * HTML5.
   */
  case object HTML5 extends OutputFormat {
    val name = "html5"
  }

  /**
   * DocBook XML.
   */
  case object DocBook extends InputFormat with OutputFormat {
    val name = "docbook"
  }

  /**
   * MediaWiki markup.
   */
  case object MediaWiki extends InputFormat with OutputFormat {
    val name = "mediawiki"
  }

  /**
   * LaTeX.
   */
  case object LaTeX extends InputFormat with OutputFormat {
    val name = "latex"
  }

  /**
   * LaTeX beamer slide show.
   */
  case object Beamer extends OutputFormat {
    val name = "beamer"
  }

  /**
   * ConTeXt.
   */
  case object ConTeXt extends OutputFormat {
    val name = "context"
  }

  /**
   * Groff man.
   */
  case object Man extends OutputFormat {
    val name = "man"
  }

  /**
   * PDF.
   *
   * Requires a LaTeX library to be installed.
   */
  case object PDF extends OutputFormat {
    val name = "pdf"
  }

  /**
   * RTF (Rich Text Format).
   */
  case object RTF extends OutputFormat {
    val name = "rtf"
  }

  /**
   * ASCIIDoc.
   */
  case object ASCIIDoc extends OutputFormat {
    val name = "asciidoc"
  }

  /**
   * Emacs Org-Mode.
   */
  case object Org extends OutputFormat {
    val name = "org"
  }

  /**
   * GNU Texinfo.
   */
  case object Texinfo extends OutputFormat {
    val name = "texinfo"
  }

  /**
   * OpenDocument XML.
   */
  case object OpenDocument extends OutputFormat {
    val name = "opendocument"
  }

  /**
   * OpenOffice text document.
   */
  //case object ODT extends OutputFormat {
  //  val name = "odt"
  //}

  /**
   * Word DOCX.
   */
  case object DOCX extends OutputFormat {
    val name = "docx"
  }

  /**
   * EPUB.
   */
  //case object EPUB extends OutputFormat {
  //  val name = "epub"
  //}

  /**
   * EPUB v3.
   */
  //case object EPUB3 extends OutputFormat {
  //  val name = "epub3"
  //}

  /**
   * FictionBook2 e-book.
   */
  case object FB2 extends OutputFormat {
    val name = "fb2"
  }

  /**
   * Slidy HTML and JavaScript slide show.
   */
  case object Slidy extends OutputFormat {
    val name = "slidy"
  }

  /**
   * Slideous HTML and JavaScript slide show.
   */
  case object Slideous extends OutputFormat {
    val name = "slideous"
  }

  /**
   * DZSlides HTML and JavaScript slide show.
   */
  case object DZSlides extends OutputFormat {
    val name = "dzslides"
  }

  /**
   * S5 HTML and JavaScript slide show.
   */
  case object S5 extends OutputFormat {
    val name = "s5"
  }
}