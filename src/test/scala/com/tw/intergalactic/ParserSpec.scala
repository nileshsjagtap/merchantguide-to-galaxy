package com.tw.intergalactic

import com.tw.intergalactic.core._
import com.tw.intergalactic.violations.{InvalidISubtractions, InvalidMetalName, UnrecognisedAlienNumber, UnrecognisedSentence}
import org.scalatest.{FunSpec, Matchers}

class ParserSpec extends FunSpec with Matchers {

  describe("Parser") {
    it("should parse sentence containing Alien Number data") {
      //given
      val sentence = "prok is V"

      //when
      val data = Parser.parseOneSentence(sentence).right.get.asInstanceOf[AlienNumberStatement]

      //then
      data.name shouldEqual "prok"
      data.romanNumeral shouldEqual "V"
    }

    it("should fail if sentence does not contain Alien Number data") {
      //given
      val sentence = "prok is W"

      //when
      val data = Parser.parseOneSentence(sentence).left.get

      //then
      data shouldEqual UnrecognisedSentence
    }

    it("should parse sentence containing Metal data") {
      //given
      val sentence = "glob glob Silver is 34 Credits"

      //when
      val data = Parser.parseOneSentence(sentence).right.get.asInstanceOf[MetalStatement]

      //then
      data.name shouldEqual "Silver"
      data.alienValue shouldEqual List("glob", "glob")
      data.price shouldEqual 34
    }
    it("should not parse sentence if Metal data not present") {
      //given
      val sentence = "glob is 34 Credits"

      //when
      val data = Parser.parseOneSentence(sentence).left.get

      //then
      data shouldEqual UnrecognisedSentence
    }

    it("should parse Alien number question") {
      //given
      val sentence = "how much is pish tegj glob glob ?"

      //when
      val data = Parser.parseOneSentence(sentence).right.get.asInstanceOf[AlienNumberQuestion]

      //then
      data.alienValue shouldEqual List("pish", "tegj", "glob", "glob")
    }
    it("should not parse Alien number question if data is missing") {
      //given
      val sentence = "how much is  ?"

      //when
      val data = Parser.parseOneSentence(sentence).right.get

      //then
      data shouldEqual UnrecognisedQuestion("I have no idea what you are talking about")
    }
    it("should parse metal question") {
      //given
      val sentence = "how many Credits is glob prok Iron ?"

      //when
      val data = Parser.parseOneSentence(sentence).right.get.asInstanceOf[MetalQuestion]

      //then
      data.name shouldEqual "Iron"
      data.alienValue shouldEqual List("glob", "prok")
    }
    it("should not parse metal question if data is missing") {
      //given
      val sentence = "how many Credits is glob ?"

      //when
      val data = Parser.parseOneSentence(sentence).right.get

      //then
      data shouldEqual UnrecognisedQuestion("I have no idea what you are talking about")
    }
    it("should parse alien number sentences") {
      //given
      val sentence1 = "glob is I"
      val sentence2 = "prok is V"
      val sentence3 = "pish is X"
      val sentence4 = "tegj is L"
      val sentence5 = "glob glob Silver is 34.0 Credits"
      val sentence6 = "how much is pish tegj glob glob ?"
      val sentence7 = "how many Credits is glob prok Silver ?"
      val sentence8 = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
      val sentences = List(sentence1, sentence2, sentence3, sentence4, sentence5, sentence6, sentence7, sentence8)
      val expected = List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L"))

      //when
      val data = Parser.parseAlienNumberStatements(sentences).right.get

      //then
      data shouldEqual expected
    }
    it("should parse metal sentences") {
      //given
      val sentence1 = "glob is I"
      val sentence2 = "prok is V"
      val sentence3 = "pish is X"
      val sentence4 = "tegj is L"
      val sentence5 = "glob glob Silver is 34.0 Credits"
      val sentence6 = "how much is pish tegj glob glob ?"
      val sentence7 = "how many Credits is glob prok Silver ?"
      val sentence8 = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
      val sentences = List(sentence1, sentence2, sentence3, sentence4, sentence5, sentence6, sentence7, sentence8)
      val alienLanguage = AlienLanguage(List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L")))
      val expected = List(MetalStatement("Silver", List("glob", "glob"), 34.0))

      //when
      val data = Parser.parseMetalStatements(sentences, alienLanguage).right.get

      //then
      data shouldEqual expected
    }
    it("should parse questions") {
      //given
      val sentence1 = "glob is I"
      val sentence2 = "prok is V"
      val sentence3 = "pish is X"
      val sentence4 = "tegj is L"
      val sentence5 = "glob glob Silver is 34.0 Credits"
      val sentence6 = "how much is pish tegj glob glob ?"
      val sentence7 = "how many Credits is glob prok Silver ?"
      val sentence8 = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
      val sentences = List(sentence1, sentence2, sentence3, sentence4, sentence5, sentence6, sentence7, sentence8)
      val alienLanguage = AlienLanguage(List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L")))
      val metalStatement = Parser.parseMetalStatements(sentences, alienLanguage).right.get
      val metalDictionary = MetalDictionary(metalStatement, alienLanguage)
      val expected = List(AlienNumberQuestion(List("pish", "tegj", "glob", "glob")), MetalQuestion(List("glob", "prok"), "Silver"), UnrecognisedQuestion("I have no idea what you are talking about"))

      //when
      val data = Parser.parseAllQuestions(sentences, alienLanguage, metalDictionary).right.get

      //then
      data shouldEqual expected
    }
    it("should give error message if data is missing") {
      //given
      val sentence1 = "glob is I"
      val sentence2 = "prok is V"
      val sentence3 = "pish is X"
      val sentence4 = "tegj is L"
      val sentence5 = "glob glob Silver is 34.0 Credits"
      val sentence6 = "how much is pish tegj glob glob ?"
      val sentence7 = "how many Credits is glob prok Silver ?"
      val sentence8 = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
      val sentences = List(sentence1, sentence2, sentence3, sentence4, sentence5, sentence6, sentence7, sentence8)
      val alienLanguage = AlienLanguage(List(AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L")))
      val expected = UnrecognisedAlienNumber

      //when
      val actual = Parser.parseMetalStatements(sentences, alienLanguage).left.get

      //then
      actual shouldEqual expected
    }
    it("should give error message if invalid metal name found") {
      //given
      val sentence1 = "glob is I"
      val sentence2 = "prok is V"
      val sentence3 = "pish is X"
      val sentence4 = "tegj is L"
      val sentence5 = "glob glob pish is 34.0 Credits"
      val sentence6 = "how much is pish tegj glob glob ?"
      val sentence7 = "how many Credits is glob prok Silver ?"
      val sentence8 = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
      val sentences = List(sentence1, sentence2, sentence3, sentence4, sentence5, sentence6, sentence7, sentence8)
      val alienLanguage = AlienLanguage(List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L")))
      val expected = InvalidMetalName

      //when
      val data = Parser.parseMetalStatements(sentences, alienLanguage).left.get

      //then
      data shouldEqual expected
    }
    it("should give error message if invalid roman numbr found") {
      //given
      val sentence1 = "glob is I"
      val sentence2 = "prok is V"
      val sentence3 = "pish is X"
      val sentence4 = "tegj is L"
      val sentence5 = "glob tegj Silver is 34.0 Credits"
      val sentence6 = "how much is pish tegj glob glob ?"
      val sentence7 = "how many Credits is glob prok Silver ?"
      val sentence8 = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
      val sentences = List(sentence1, sentence2, sentence3, sentence4, sentence5, sentence6, sentence7, sentence8)
      val alienLanguage = AlienLanguage(List(AlienNumberStatement("glob", "I"), AlienNumberStatement("prok", "V"), AlienNumberStatement("pish", "X"), AlienNumberStatement("tegj", "L")))
      val expected = InvalidISubtractions

      //when
      val data = Parser.parseMetalStatements(sentences, alienLanguage).left.get

      //then
      data shouldEqual expected
    }
    it("should give error message if question is out of context") {
      //given
      val sentence = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"

      //when
      val data = Parser.parseOneSentence(sentence).right.get

      //then
      data shouldEqual UnrecognisedQuestion("I have no idea what you are talking about")
    }
  }

}
