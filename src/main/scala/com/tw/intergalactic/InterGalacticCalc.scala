package com.tw.intergalactic

import com.tw.intergalactic.core.{AlienLanguage, MetalDictionary}
import com.tw.intergalactic.violations.Violation

case class InterGalacticCalc(sentences: Sentences) {

  def answers(): Either[Violation, Answers] =
    for {
      alienNumberStatements <- Parser.parseAlienNumberStatements(sentences)
      alienLanguage = AlienLanguage(alienNumberStatements)
      metalStatements <- Parser.parseMetalStatements(sentences, alienLanguage)
      metalDictionary = MetalDictionary(metalStatements, alienLanguage)
      questions <- Parser.parseAllQuestions(sentences, alienLanguage, metalDictionary)
    } yield Calculator(alienLanguage, metalDictionary).generateAnswers(questions)

}
