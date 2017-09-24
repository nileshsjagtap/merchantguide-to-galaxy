package com.tw.intergalactic

import java.io.{File, FileOutputStream}

import com.tw.intergalactic.core._
import com.tw.intergalactic.utils.{TextReader, TextWriter}
import org.scalatest.{FunSpec, Matchers}

class IntegrationSpec extends FunSpec with Matchers {

  private val TEST_FILE_PATH = "./IntegrationSpec.txt"
  private def deleteTestFile(): Unit = new File(TEST_FILE_PATH).delete()
  private def createTestFile() = {
    val file: File = new File(TEST_FILE_PATH)
    val outputStream = new FileOutputStream(file)
    outputStream.write("how many Credits is glob prok Silver ?\n".toCharArray.map(_.toByte))
    outputStream.write("glob is I\n".toCharArray.map(_.toByte))
    outputStream.write("glob glob Silver is 34 Credits\n".toCharArray.map(_.toByte))
    outputStream.write("glob prok Gold is 57800 Credits\n".toCharArray.map(_.toByte))
    outputStream.write("tegj is L\n".toCharArray.map(_.toByte))
    outputStream.write("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?\n".toCharArray.map(_.toByte))
    outputStream.write("how many Credits is glob prok Iron ?\n".toCharArray.map(_.toByte))
    outputStream.write("how much is pish tegj glob glob ?\n".toCharArray.map(_.toByte))
    outputStream.write("pish is X\n".toCharArray.map(_.toByte))
    outputStream.write("how many Credits is glob prok Gold ?\n".toCharArray.map(_.toByte))
    outputStream.write("what is your name ?\n".toCharArray.map(_.toByte))
    outputStream.write("prok is V\n".toCharArray.map(_.toByte))
    outputStream.write("pish pish Iron is 3910 Credits\n".toCharArray.map(_.toByte))
    outputStream.close()
  }

  describe("integration test") {

    it("should run successfully") {
      //given
      createTestFile()
      val expected = List(
        "glob prok Silver is 68 Credits",
        "I have no idea what you are talking about",
        "glob prok Iron is 782 Credits",
        "pish tegj glob glob is 42",
        "glob prok Gold is 57800 Credits",
        "I have no idea what you are talking about")

      //when
      Run.run(TEST_FILE_PATH, TEST_FILE_PATH)
      val actual = new TextReader(TEST_FILE_PATH).readSentences()

      //then
      actual shouldEqual expected
      deleteTestFile()
    }

    it("should parse validate calculate answers and write to file") {
      //given
      val line1 = "glob is I"
      val line5 = "glob glob Silver is 34 Credits"
      val line8 = "how much is pish tegj glob glob ?"
      val line9 = "how many Credits is glob prok Silver ?"
      val line2 = "prok is V"
      val line3 = "pish is X"
      val line4 = "tegj is L"
      val line6 = "glob prok Gold is 57800 Credits"
      val line7 = "pish pish Iron is 3910 Credits"
      val line10 = "how many Credits is glob prok Gold ?"
      val line11 = "how many Credits is glob prok Iron ?"
      val line12 = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
      val lines = List(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11, line12)

      val alienStatements = Parser.parseAlienNumberStatements(lines).right.get
      val alienLanguage = new AlienLanguage(alienStatements)
      val metalStatement = Parser.parseMetalStatements(lines, alienLanguage).right.get
      val metalDictionary = MetalDictionary(metalStatement, alienLanguage)
      val questions = Parser.parseAllQuestions(lines, alienLanguage, metalDictionary).right.get

      val expected = List(
        "pish tegj glob glob is 42",
        "glob prok Silver is 68 Credits",
        "glob prok Gold is 57800 Credits",
        "glob prok Iron is 782 Credits",
        "I have no idea what you are talking about")

      //when
      val sentences = Calculator(alienLanguage, metalDictionary).generateAnswers(questions)

      new TextWriter().writeToFile(sentences, TEST_FILE_PATH)
      val actual = new TextReader(TEST_FILE_PATH).readSentences()

      //then
      actual shouldEqual expected
      deleteTestFile()
    }

    it("should parse validate and calculate answers") {
      //given
      val line1 = "glob is I"
      val line5 = "glob glob Silver is 34 Credits"
      val line8 = "how much is pish tegj glob glob ?"
      val line9 = "how many Credits is glob prok Silver ?"
      val line2 = "prok is V"
      val line3 = "pish is X"
      val line4 = "tegj is L"
      val line6 = "glob prok Gold is 57800 Credits"
      val line7 = "pish pish Iron is 3910 Credits"
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
      val actual: List[Sentence] = Calculator(alienLanguage, metalDictionary).generateAnswers(questions)

      //then
      actual shouldEqual expected
    }

  }

}
