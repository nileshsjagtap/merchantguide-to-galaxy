package com.tw.intergalactic

import com.tw.intergalactic.core._
import com.tw.intergalactic.violations._

object Parser {

  private val FIRST_SENTENCE_EXTRACTOR = AlienNumberStatementExtractor

  def parseAlienNumberStatements(sentences: Sentences): Either[Violation, List[AlienNumberStatement]] = {
    def extractAlienNumberStatements(map: Map[String, List[Sentence]]): List[AlienNumberStatement] = {
      map("AlienNumberStatement").map {
        case AlienNumberStatement(x, y) => AlienNumberStatement(x, y)
      }
    }

    val either: Either[Violation, List[Sentence]] = sequence[Sentence](sentences.map(parseOneSentence))
    either.map(Sentence.groupBySentences).map(extractAlienNumberStatements)
  }

  private def sequence[T](eitherList: List[Either[Violation, T]]): Either[Violation, List[T]] = {
    def go(remaining: List[Either[Violation, T]], accumulator: List[T], violation: Violation): Either[Violation, List[T]] = {
      remaining match {
        case List() => Right(accumulator)
        case h :: t => h match {
          case Left(x) => Left(x)
          case Right(x) => go(t, accumulator ++ List(x), violation)
        }
      }
    }

    go(eitherList, List[T](), UnrecognisedSentence)
  }

  def parseOneSentence(sentence: String): Either[Violation, Sentence] = {
    def iterate(extractor: SentenceExtractor): Either[Violation, Sentence] = {
      if (extractor.validate(sentence)) Right(extractor.extract(sentence))
      else if (extractor == extractor.next) Left(UnrecognisedSentence)
      else iterate(extractor.next)
    }

    iterate(FIRST_SENTENCE_EXTRACTOR)
  }

  def parseMetalStatements(sentences: Sentences, alienLanguage: AlienLanguage): Either[Violation, List[MetalStatement]] = {
    def validateStatements(map: Map[String, List[Sentence]]): Either[Violation, List[MetalStatement]] = {
      val metalStatements: List[MetalStatement] = map("MetalStatement").map {
        case MetalStatement(x, y, z) => MetalStatement(x, y, z)
      }
      sequence[MetalStatement](metalStatements.map(ParserValidator.validateMetalStatement(_, alienLanguage)))
    }

    val either: Either[Violation, List[Sentence]] = sequence[Sentence](sentences.map(parseOneSentence))
    either.map(Sentence.groupBySentences).flatMap(validateStatements)
  }

  def parseAllQuestions(sentences: Sentences, alienLanguage: AlienLanguage, metalDictionary: MetalDictionary): Either[Violation, Questions] = {
    def validateQuestions(list: Questions): Either[Violation, Questions] = {
      sequence[Sentence](list.map(ParserValidator.validateQuestion(_, alienLanguage, metalDictionary)))
    }

    val either: Either[Violation, List[Sentence]] = sequence[Sentence](sentences.map(parseOneSentence))
    either.map(Sentence.sentencesToQuestions).flatMap(validateQuestions)
  }

}

