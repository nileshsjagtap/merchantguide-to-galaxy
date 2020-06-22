package com.tw.domain

import com.tw.core.Sequenceable
import com.tw.implicits.AppImplicits._

case class InterGalacticCalculator(alienNumbers: AlienNumbers, metals: Metals) extends Sequenceable {

  private val NEWLINE_CHAR = "\n"
  private val SPACE_CHAR = " "

  def evaluateQuestions(sentences: List[Sentence]) = sequence(sentences.map(evaluateQuestion).filter(_.isRight))

  def evaluateQuestion(sentence: Sentence) = sentence match {
    case question: AlienNumberQuestion => evaluateAlienNumberQuestion(question)
    case question: MetalQuestion => evaluateMetalQuestion(question)
    case UnrecognisedQuestion => Right(UnrecognisedQuestionAnswer())
    case _ => Left(List(InvalidQuestion))
  }

  private def evaluateAlienNumberQuestion(alienNumberQuestion: AlienNumberQuestion) =
    alienNumbers.romanNumeralsFor(alienNumberQuestion.alienTokens).flatMap(RomanNumber.createFromNumerals)
      .fold(
        error => Left(List(InvalidAlienNumberQuestion, error)),
        romanNumber => Right(
          AlienNumberAnswer(alienNumberQuestion.alienTokens, romanNumber.value)
        ))

  private def evaluateMetalQuestion(metalQuestion: MetalQuestion) =
    alienNumbers.romanNumeralsFor(metalQuestion.alienTokens).flatMap(RomanNumber.createFromNumerals)
      .fold(
        error => Left(List(InvalidMetalQuestion, error)),
        romanNumber => createMetalAnswer(metalQuestion, romanNumber))

  private def createMetalAnswer(metalQuestion: MetalQuestion, romanNumber: RomanNumber) =
    metals.get(metalQuestion.metalName) match {
      case Left(error) => Left(List(error))
      case Right(metal) => Right(MetalInfoAnswer(
        metalQuestion.alienTokens,
        metalQuestion.metalName,
        metal.perUnitValue * romanNumber.value
      ))
    }

  def generateAnswersFor(sentences: List[Sentence]) = sentences.map(generateAnswerFor).mkString(NEWLINE_CHAR)

  def generateAnswerFor(sentence: Sentence) = if (validAnswer(sentence)) sentence.show else ""

  private def validAnswer(sentence: Sentence) = sentence match {
    case _: AlienNumberAnswer => true
    case _: MetalInfoAnswer => true
    case _: UnrecognisedQuestionAnswer => true
    case _ => false
  }

}