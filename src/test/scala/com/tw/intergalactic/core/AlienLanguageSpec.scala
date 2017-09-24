package com.tw.intergalactic.core

import com.tw.intergalactic.core.RomanNumber.{I, L, V, X}
import org.scalatest.{FunSpec, Matchers}

class AlienLanguageSpec extends FunSpec with Matchers {

  describe("alien language") {

    it("should create alienToRomanNumberMap") {
      //given
      val statements = List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L"))
      val alienLanguage = new AlienLanguage(statements)
      val expected = Map("glob" -> I, "prok" -> V, "pish" -> X, "tegj" -> L)

      //when
      val actual = alienLanguage.alienToRomanNumberMap

      //then
      actual shouldEqual expected
    }

    it("should convert string to roman number") {
      //given
      val statements = List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L"))
      val alienLanguage = new AlienLanguage(statements)
      val string = "glob"
      val expected = I

      //when
      val actual = alienLanguage.stringToRomanNumber(string)

      //then
      actual shouldEqual expected
    }

    it("should convert strings to roman number list") {
      //given
      val statements = List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L"))
      val alienLanguage = new AlienLanguage(statements)
      val strings = List("pish", "glob", "prok")
      val expected = List(X, I, V)

      //when
      val actual = alienLanguage.stringsToRomanNumberList(strings)

      //then
      actual shouldEqual expected
    }

    it("should validate list of alien numbers") {
      //given
      val statements = List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L"))
      val alienLanguage = new AlienLanguage(statements)
      val strings = List("pish", "gslob", "prok")
      val expected = false

      //when
      val actual = alienLanguage.validAlienTokens(strings)

      //then
      actual shouldEqual expected
    }

    it("should validate alien number") {
      //given
      val statements = List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L"))
      val alienLanguage = new AlienLanguage(statements)
      val strings = "pish"
      val expected = true

      //when
      val actual = alienLanguage.contains(strings)

      //then
      actual shouldEqual expected
    }

  }

}
