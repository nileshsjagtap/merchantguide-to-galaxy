package com.tw.intergalactic.utils

import scala.io.Source

class TextReader(filePath: String) {

  def readSentences(): List[String] = Source.fromFile(filePath).getLines.toList

}
