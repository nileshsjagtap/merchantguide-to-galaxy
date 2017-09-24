package com.tw.intergalactic.utils

import java.io.{File, FileOutputStream}

import org.scalatest.{FunSpec, Matchers}

class TextReaderSpec extends FunSpec with Matchers {

  private val TEST_FILE_PATH = "./TextReaderSpec.txt"
  private def deleteTestFile(): Unit = new File(TEST_FILE_PATH).delete()
  private def createTestFile() = {
    val file: File = new File(TEST_FILE_PATH)
    val outputStream = new FileOutputStream(file)
    outputStream.write("glob is I\n".toCharArray.map(_.toByte))
    outputStream.write("glob glob Silver is 34 Credits\n".toCharArray.map(_.toByte))
    outputStream.write("how much is pish tegj glob glob ?\n".toCharArray.map(_.toByte))
    outputStream.write("how many Credits is glob prok Silver ?\n".toCharArray.map(_.toByte))
    outputStream.write("prok is V\n".toCharArray.map(_.toByte))
    outputStream.write("pish is X\n".toCharArray.map(_.toByte))
    outputStream.write("tegj is L\n".toCharArray.map(_.toByte))
    outputStream.write("glob prok Gold is 57800 Credits\n".toCharArray.map(_.toByte))
    outputStream.write("pish pish Iron is 3910 Credits\n".toCharArray.map(_.toByte))
    outputStream.write("how many Credits is glob prok Gold ?\n".toCharArray.map(_.toByte))
    outputStream.write("how many Credits is glob prok Iron ?\n".toCharArray.map(_.toByte))
    outputStream.write("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?\n".toCharArray.map(_.toByte))
    outputStream.close()
  }

  describe("Text Reader") {

    it("should read file line by line") {
      //given
      createTestFile()
      val expected = List("glob is I", "glob glob Silver is 34 Credits", "how much is pish tegj glob glob ?", "how many Credits is glob prok Silver ?", "prok is V", "pish is X", "tegj is L", "glob prok Gold is 57800 Credits", "pish pish Iron is 3910 Credits", "how many Credits is glob prok Gold ?", "how many Credits is glob prok Iron ?", "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?")

      //when
      val actual: List[String] = new TextReader(TEST_FILE_PATH).readSentences()

      //then
      actual shouldEqual expected
      deleteTestFile()
    }

  }

}
