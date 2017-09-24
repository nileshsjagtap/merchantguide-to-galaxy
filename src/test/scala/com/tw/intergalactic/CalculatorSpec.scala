package com.tw.intergalactic

import com.tw.intergalactic.core._
import org.scalatest.{FunSpec, Matchers}

class CalculatorSpec extends FunSpec with Matchers {

  describe("Alien Calculator") {

    it("should calculate all answers") {
      //given
      val line1 = "glob is I"
      val line2 = "prok is V"
      val line3 = "pish is X"
      val line4 = "tegj is L"
      val line5 = "glob glob Silver is 34 Credits"
      val line6 = "glob prok Gold is 57800 Credits"
      val line7 = "pish pish Iron is 3910 Credits"
      val line8 = "how much is pish tegj glob glob ?"
      val line9 = "how many Credits is glob prok Silver ?"
      val line10 = "how many Credits is glob prok Gold ?"
      val line11 = "how many Credits is glob prok Iron ?"
      val line12 = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
      val lines = List(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11, line12)

      val alienStatements = Parser.parseAlienNumberStatements(lines).right.get
      val alienLanguage = AlienLanguage(alienStatements)
      val metalStatement = Parser.parseMetalStatements(lines, alienLanguage).right.get
      val metalDictionary = MetalDictionary(metalStatement, alienLanguage)
      val questions = Parser.parseAllQuestions(lines, alienLanguage, metalDictionary).right.get

      val expected = List(
        AlienNumberAnswer(List("pish", "tegj", "glob", "glob"), 42),
        MetalAnswer(List("glob", "prok"), "Silver", 68),
        MetalAnswer(List("glob", "prok"), "Gold", 57800),
        MetalAnswer(List("glob", "prok"), "Iron", 782),
        UnrecognisedQuestion("I have no idea what you are talking about"))

      //when
      val actual = Calculator(alienLanguage, metalDictionary).generateAnswers(questions)

      //then
      actual shouldEqual expected
    }

  }

}
