package com.tw.intergalactic.utils

import java.io.File

import com.tw.intergalactic.core.{AlienNumberAnswer, MetalAnswer, UnrecognisedQuestion}
import org.scalatest.{FunSpec, Matchers}

class TextWriterSpec extends FunSpec with Matchers {

  private val TEST_FILE_PATH = "./TextWriterSpec.txt"
  private def deleteTestFile(): Unit = new File(TEST_FILE_PATH).delete()

  describe("Test Writer") {

    it("should generate output content") {
      //given
      val outputSentenceContexts = List(
        AlienNumberAnswer(List("pish", "tegj", "glob", "glob"), 42),
        MetalAnswer(List("glob", "prok"), "Silver", 68),
        MetalAnswer(List("glob", "prok"), "Gold", 57800),
        MetalAnswer(List("glob", "prok"), "Iron", 782),
        UnrecognisedQuestion("I have no idea what you are talking about"))
      val expected =
        "pish tegj glob glob is 42\n" +
          "glob prok Silver is 68 Credits\n" +
          "glob prok Gold is 57800 Credits\n" +
          "glob prok Iron is 782 Credits\n" +
          "I have no idea what you are talking about\n"

      //when
      val actual = new TextWriter().generateOutputContent(outputSentenceContexts)

      //then
      actual shouldEqual expected
    }

    it("should generate output content and write to file") {
      //given
      val outputSentenceContexts = List(
        AlienNumberAnswer(List("pish", "tegj", "glob", "glob"), 42),
        MetalAnswer(List("glob", "prok"), "Silver", 68),
        MetalAnswer(List("glob", "prok"), "Gold", 57800),
        MetalAnswer(List("glob", "prok"), "Iron", 782),
        UnrecognisedQuestion("I have no idea what you are talking about"))
      val expected = List(
        "pish tegj glob glob is 42",
        "glob prok Silver is 68 Credits",
        "glob prok Gold is 57800 Credits",
        "glob prok Iron is 782 Credits",
        "I have no idea what you are talking about")

      //when
      new TextWriter().writeToFile(outputSentenceContexts, TEST_FILE_PATH)
      val actual = new TextReader(TEST_FILE_PATH).readSentences()

      //then
      actual shouldEqual expected
      deleteTestFile()
    }

  }

}
