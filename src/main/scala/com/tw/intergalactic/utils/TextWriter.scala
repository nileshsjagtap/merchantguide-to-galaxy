package com.tw.intergalactic.utils

import java.io.PrintWriter

import com.tw.intergalactic.core.{AlienNumberAnswer, MetalAnswer, Sentence, UnrecognisedQuestion}

class TextWriter {

  private val NEWLINE_CHAR = "\n"
  private val SPACE_CHAR = " "

  def writeToFile(sentenceList: List[Sentence], filePath: String): Unit = {
    new PrintWriter(filePath) {
      write(generateOutputContent(sentenceList)); close
    }
  }

  def generateOutputContent(sentenceList: List[Sentence]): String = {
    sentenceList.foldLeft("")(createContent)
  }

  private def createContent(buffer: String, sentence: Sentence): String = sentence match {
    case AlienNumberAnswer(x, y) => buffer + contentForAlienNumberAnswer(x, y) + NEWLINE_CHAR
    case MetalAnswer(x, y, z) => buffer + contentForMetalAnswer(x, y, z) + NEWLINE_CHAR
    case UnrecognisedQuestion(x) => buffer + x + NEWLINE_CHAR
    case _ => ""
  }

  private def contentForAlienNumberAnswer(alienNums: List[String], amount: Long): String = {
    alienNums.mkString(SPACE_CHAR) + SPACE_CHAR + "is" + SPACE_CHAR + amount
  }

  private def contentForMetalAnswer(alienNums: List[String], metalName: String, amount: Long): String = {
    alienNums.mkString(SPACE_CHAR) + SPACE_CHAR + metalName + SPACE_CHAR + "is" + SPACE_CHAR + amount + SPACE_CHAR + "Credits"
  }

}
