package com.tw

import java.util.regex.Pattern

import com.tw.core._
import com.tw.domain._

sealed abstract class SentenceParser {
  def matches(sentence: String): Boolean
  def parse(sentence: String): Sentence
}

object SentenceParser {
  
  private val parserList = List(
    AlienNumberStatementParser,
    MetalInfoStatementParser,
    AlienNumberQuestionParser,
    MetalQuestionParser,
    UnrecognisedQuestionParser
  )
  
  def parse(sentence: String): Sentence =
    parserList.filter(_.matches(sentence)).map(_.parse(sentence)).headOption.getOrElse(UnrecognisedSentence)

  def parse(sentences: List[String]): List[Sentence] = sentences.map(parse)

  def parseAllAlienNumberStatements(sentences: List[String]) =
    parse(sentences).filter(_.toString.contains("AlienNumberStatement"))

  def parseAllMetalInfoStatements(sentences: List[String]) =
    parse(sentences).filter(_.toString.contains("MetalStatement"))

  def parseAllAlienNumberQuestions(sentences: List[String]) =
    parse(sentences).filter(_.toString.contains("AlienNumberQuestion"))

  def parseAllMetalQuestions(sentences: List[String]) =
    parse(sentences).filter(_.toString.contains("MetalQuestion"))

  def parseAllUnrecognisedQuestion(sentences: List[String]) =
    parse(sentences).filter(_.toString.contains("UnrecognisedQuestion"))

}

case object AlienNumberStatementParser extends SentenceParser {
  private val REGEX = "(\\w+) +is +([I|V|X|L|C|D|M])"
  override def matches(sentence: String) = sentence.matches(REGEX)
  override def parse(sentence: String) = AlienNumberStatement(sentence.split(" ")(0), sentence.trim.split(" ")(2))
}

case object MetalInfoStatementParser extends SentenceParser {
  private val REGEX = "([\\w+\\s]+?) +(\\w+) +is +(\\d+\\.?\\d+) +Credits"
  override def matches(sentence: String) = sentence.matches(REGEX)
  override def parse(sentence: String) = {
    val m = Pattern.compile(REGEX).matcher(sentence)
    m.find()
    MetalStatement(m.group(2), m.group(1).trim.split(" ").toList, m.group(3).toDouble)
  }
}

case object AlienNumberQuestionParser extends SentenceParser {
  private val REGEX = "how +much +is +([\\w+ ]+) +\\?"
  override def matches(sentence: String) = sentence.matches(REGEX)
  override def parse(sentence: String) = {
    val m = Pattern.compile(REGEX).matcher(sentence)
    m.find()
    AlienNumberQuestion(m.group(1).trim.split(" ").toList)
  }
}

case object MetalQuestionParser extends SentenceParser {
  private val REGEX = "how +many +Credits +is +([\\w+\\s]+) +(\\w+) +\\?"
  override def matches(sentence: String) = sentence.matches(REGEX)
  override def parse(sentence: String) = {
    val m = Pattern.compile(REGEX).matcher(sentence)
    m.find()
    MetalQuestion(m.group(1).trim.split(" ").toList, m.group(2))
  }
}

case object UnrecognisedQuestionParser extends SentenceParser {
  override def matches(sentence: String) = sentence.endsWith("?")
  override def parse(sentence: String) = UnrecognisedQuestion
}