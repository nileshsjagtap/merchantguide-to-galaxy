package com.tw.app

import java.io.PrintWriter

import com.tw.SentenceParser
import com.tw.domain._
import com.tw.implicits.AppImplicits._

import scala.io.Source
import scala.util.{Failure, Success, Try}

object Run extends App {

  override def main(args: Array[String]) =
    if (!validArguments(args)) System.err.println("Error: Either Input File Path And/Or Output File Path are missing or blank.")
    else run(args(0), args(1))

  private def validArguments(args: Array[String]) = {
    args.length == 2 && args(0) != null && args(1) != null && !args(0).equals("") && !args(1).equals("")
  }

  def run(inputFilePath: String, outputFilePath: String) =
    for {
      sentences <- readLines(inputFilePath)

      alienNumberStatements = SentenceParser.parseAllAlienNumberStatements(sentences)
      alienNumbers <- alienNumberStatements.validate[AlienNumbers]

      metalInfoStatements = SentenceParser.parseAllMetalInfoStatements(sentences)
      metals <- MetalsInput(metalInfoStatements, alienNumbers).validate[Metals]

      interGalacticCalculator = InterGalacticCalculator(alienNumbers, metals)

      alienNumberQuestions = SentenceParser.parseAllAlienNumberQuestions(sentences)
      metalQuestions = SentenceParser.parseAllMetalQuestions(sentences)
      unrecognisedQuestions = SentenceParser.parseAllUnrecognisedQuestion(sentences)

      alienNumberAnswers <- interGalacticCalculator.evaluateQuestions(alienNumberQuestions)
      metalInfoAnswers <- interGalacticCalculator.evaluateQuestions(metalQuestions)
      unrecognisedQuestionAnswers <- interGalacticCalculator.evaluateQuestions(unrecognisedQuestions)

      alienNumberAnswersText = interGalacticCalculator.generateAnswersFor(alienNumberAnswers)
      metalInfoAnswersText = interGalacticCalculator.generateAnswersFor(metalInfoAnswers)
      unrecognisedQuestionAnswersText = interGalacticCalculator.generateAnswersFor(unrecognisedQuestionAnswers)

      charsWrittenToFile <- writeTextToFile(alienNumberAnswersText + "\n" + metalInfoAnswersText + "\n" + unrecognisedQuestionAnswersText, outputFilePath)
    } yield charsWrittenToFile

  def readLines(filePath: String) =
    Try(Source.fromFile(filePath).getLines.toList) match {
      case Failure(_) => Left(ErrorReadingFromFile)
      case Success(lines) => Right(lines)
    }

  def writeTextToFile(text: String, filePath: String) =
    Try(new PrintWriter(filePath) {
      write(text)
      close()
    }) match {
      case Failure(_) => Left(ErrorWritingToFile)
      case Success(_) => Right(text.length)
    }
}