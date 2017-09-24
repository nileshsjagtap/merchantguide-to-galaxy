package com.tw.intergalactic.core

import org.scalatest.{FunSpec, Matchers}

class MetalDictionarySpec extends FunSpec with Matchers {

  describe("metal dictionary") {

    it("should create metals") {
      //given
      val alienLanguage = new AlienLanguage(List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L")))
      val metalStatements = List(MetalStatement("Silver", List("glob", "glob"), 34.0), MetalStatement("Gold", List("glob", "glob"), 341.0), MetalStatement("Iron", List("glob", "glob"), 324.0))
      val metalDictionary = MetalDictionary(metalStatements, alienLanguage)
      val expected = Map("Silver" -> Metal("Silver", 17.0, List("glob", "glob")), "Gold" -> Metal("Gold", 170.5, List("glob", "glob")), "Iron" -> Metal("Iron", 162.0, List("glob", "glob")))

      //when
      val actual = metalDictionary.metals

      //then
      actual shouldEqual expected
    }

    it("should return matching metal instance") {
      //given
      val alienLanguage = new AlienLanguage(List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L")))
      val metalStatements = List(MetalStatement("Silver", List("glob", "glob"), 34.0), MetalStatement("Gold", List("glob", "glob"), 341.0), MetalStatement("Iron", List("glob", "glob"), 324.0))
      val metalDictionary = MetalDictionary(metalStatements, alienLanguage)
      val expected = Metal("Silver", 17.0, List("glob", "glob"))

      //when
      val actual = metalDictionary.getMetal("Silver")

      //then
      actual shouldEqual expected
    }

  }

}
