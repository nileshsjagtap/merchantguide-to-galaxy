package com.tw.domain

import com.tw.core.{Parse, Show}

sealed trait Sentence

trait Statement extends Sentence
trait Question extends Sentence
trait Answer extends Sentence

case object UnrecognisedSentence extends Sentence

case class AlienNumberStatement(alienNumber: String, romanNumeral: String) extends Statement

object AlienNumberStatement {
  implicit def parseAlienNumberStatement = new Parse[Sentence, AlienNumberStatement] {
    override def parse(sentence: Sentence) = ???
  }
}
case class MetalStatement(metalName: String, alienTokens: List[String], totalPrice: Double) extends Statement

case class AlienNumberQuestion(alienTokens: List[String]) extends Question
case class MetalQuestion(alienTokens: List[String], metalName: String) extends Question
case object UnrecognisedQuestion extends Question

case class AlienNumberAnswer(alienTokens: List[String], totalAmount: Double) extends Answer

object AlienNumberAnswer {
  private val SPACE_CHAR = " "

  implicit def alienNumberAnswerShow = new Show[AlienNumberAnswer] {
    override def show(alienNumberAnswer: AlienNumberAnswer): String =
      alienNumberAnswer.alienTokens.mkString(SPACE_CHAR) + SPACE_CHAR + "is" + SPACE_CHAR + alienNumberAnswer.totalAmount
  }
}

case class MetalInfoAnswer(alienTokens: List[String], metalName: String, totalAmount: Double) extends Answer

object MetalInfoAnswer {
  private val SPACE_CHAR = " "

  implicit def metalInfoAnswerShow = new Show[MetalInfoAnswer] {
    override def show(metalAnswer: MetalInfoAnswer): String =
      metalAnswer.alienTokens.mkString(SPACE_CHAR) + SPACE_CHAR + metalAnswer.metalName + SPACE_CHAR + "is" + SPACE_CHAR + metalAnswer.totalAmount + SPACE_CHAR + "Credits"
  }
}

case class UnrecognisedQuestionAnswer() extends Answer

object UnrecognisedQuestionAnswer {
  implicit def unrecognisedAnswerShow = new Show[UnrecognisedQuestionAnswer] {
    override def show(unrecognisedQuestionAnswer: UnrecognisedQuestionAnswer): String =
      "I have no idea what you are talking about"
  }
}