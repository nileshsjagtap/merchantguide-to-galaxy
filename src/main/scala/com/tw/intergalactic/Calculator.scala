package com.tw.intergalactic

import com.tw.intergalactic.core.RomanNumber.RomanNumber
import com.tw.intergalactic.core._

case class Calculator(alienLanguage: AlienLanguage, metalDictionary: MetalDictionary) {

  def generateAnswers(questions: Questions): Answers = {
    questions.foldLeft(List[Sentence]())(generateAns)
  }

  private def generateAns(buffer: Answers, question: Sentence): Answers = question match {
    case o@AlienNumberQuestion(_) => buffer ++ List(evaluateAlienNumberQuestion(o))
    case o@MetalQuestion(_, _) => buffer ++ List(evaluateMetalQuestion(o))
    case o@UnrecognisedQuestion(_) => buffer ++ List(o)
    case _ => buffer
  }

  private def evaluateAlienNumberQuestion(ques: AlienNumberQuestion): Sentence = {
    val romanNum: List[RomanNumber] = alienLanguage.stringsToRomanNumberList(ques.alienValue)
    AlienNumberAnswer(ques.alienValue, RomanNumber.equivalentArabicNumberFor(romanNum))
  }

  private def evaluateMetalQuestion(ques: MetalQuestion): Sentence = {
    val romanNum: List[RomanNumber] = alienLanguage.stringsToRomanNumberList(ques.alienValue)
    val units: Double = RomanNumber.equivalentArabicNumberFor(romanNum)
    val creditPerUnit: Double = metalDictionary.getMetal(ques.name).pricePerUnit
    MetalAnswer(ques.alienValue, ques.name, (creditPerUnit * units).toLong)
  }

}
