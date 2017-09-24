package com.tw.intergalactic

import java.util.regex.{Matcher, Pattern}

import com.tw.intergalactic.core._

trait SentenceExtractor {
  val next: SentenceExtractor

  def validate(sentence: String): Boolean

  def extract(sentence: String): Sentence
}

case object AlienNumberStatementExtractor extends SentenceExtractor {
  override val next: SentenceExtractor = MetalInfoStatementExtractor
  private val REGEX = "(\\w+) +is +([I|V|X|L|C|D|M])"

  override def validate(sentence: String): Boolean = sentence.matches(REGEX)

  override def extract(sentence: String): Sentence = AlienNumberStatement(sentence.split(" ")(0), sentence.trim.split(" ")(2))
}

case object MetalInfoStatementExtractor extends SentenceExtractor {
  override val next: SentenceExtractor = AlienNumberQuestionExtractor
  private val REGEX = "([\\w+\\s]+?) +(\\w+) +is +(\\d+\\.?\\d+) +Credits"

  override def validate(sentence: String): Boolean = sentence.matches(REGEX)

  override def extract(sentence: String): Sentence = {
    val m: Matcher = Pattern.compile(REGEX).matcher(sentence)
    m.find()
    MetalStatement(m.group(2), m.group(1).trim.split(" ").toList, m.group(3).toDouble)
  }
}

case object AlienNumberQuestionExtractor extends SentenceExtractor {
  override val next: SentenceExtractor = MetalQuestionExtractor
  private val REGEX = "how +much +is +([\\w+ ]+) +\\?"

  override def validate(sentence: String): Boolean = sentence.matches(REGEX)

  override def extract(sentence: String): Sentence = {
    val m: Matcher = Pattern.compile(REGEX).matcher(sentence)
    m.find()
    AlienNumberQuestion(m.group(1).trim.split(" ").toList)
  }
}

case object MetalQuestionExtractor extends SentenceExtractor {
  override val next: SentenceExtractor = UnrecognisedQuestionExtractor
  private val REGEX = "how +many +Credits +is +([\\w+\\s]+) +(\\w+) +\\?"

  override def validate(sentence: String): Boolean = sentence.matches(REGEX)

  override def extract(sentence: String): Sentence = {
    val m: Matcher = Pattern.compile(REGEX).matcher(sentence)
    m.find()
    MetalQuestion(m.group(1).trim.split(" ").toList, m.group(2))
  }
}

case object UnrecognisedQuestionExtractor extends SentenceExtractor {
  override val next: SentenceExtractor = UnrecognisedQuestionExtractor

  override def validate(sentence: String): Boolean = sentence.contains("?")

  override def extract(sentence: String): Sentence = UnrecognisedQuestion("I have no idea what you are talking about")
}