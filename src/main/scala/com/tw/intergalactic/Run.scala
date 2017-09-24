package com.tw.intergalactic

import com.tw.intergalactic.utils.{TextReader, TextWriter}

object Run {

  def main(args: Array[String]): Unit = {
    if (areValidArguments(args))
      run(args(0), args(1))
    else System.err.println("Error: Either Input File Path And/Or Output File Path are missing or blank.")
  }

  private def areValidArguments(args: Array[String]) = {
    args.length == 2 && args(0) != null && args(1) != null && !args(0).equals("") && !args(1).equals("")
  }

  def run(inputFilePath: String, outputFilePath: String): Unit = {
    val inputSentences = new TextReader(inputFilePath).readSentences()

    InterGalacticCalc(inputSentences).answers() match {
      case Left(x) => System.err.println(x)
      case Right(x) => new TextWriter().writeToFile(x, outputFilePath)
    }

  }

}
