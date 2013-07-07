scala-pandoc
============

A thin Scala wrapper around John MacFarlane's [Pandoc].

It uses Akka-style futures to write to `stdin` and read from `stdout` of the
installed `pandoc` executable. Therefore you must have Pandoc installed on your
system.

[Pandoc]: https://github.com/jgm/pandoc


Installation
------------

### SBT ###

Add the following line to your `build.sbt` file

~~~ scala
libraryDependencies += "de.vorb" % "pandoc" % "0.0.+"
~~~


Usage
-----

Here is an example.

~~~ scala
import scala.concurrent.ExecutionContext.Implicits.global

val pandocStream = Pandoc(
  from = MarkupFormat.Markdown,
  to = MarkupFormat.HTML,
  in = new FileInputStream("document.txt")
)

pandocStream.onSuccess {
  case success => println(success)  // success is the resulting HTML output of
                                    // pandoc
}

pandocStream.onFailure {
  case err => throw err
}

Await.result(pandocStream, 10.seconds)
~~~

For a more detailed example, see
[PandocTest.scala](src/test/scala/de/vorb/text/pandoc/PandocTest.scala).


License
-------

[MIT License](http://vorb.de/license/mit.html)

Copyright © 2013 Paul Vorbach

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the “Software”), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
