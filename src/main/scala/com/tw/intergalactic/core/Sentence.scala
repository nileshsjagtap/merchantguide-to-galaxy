package com.tw.intergalactic.core

import com.tw.intergalactic.Questions

trait Sentence

trait Statement extends Sentence

trait Question extends Sentence

trait Answer extends Sentence

case class AlienNumberStatement(name: String, romanNumeral: String) extends Statement

case class MetalStatement(name: String, alienValue: List[String], price: Double) extends Statement

case class AlienNumberQuestion(alienValue: List[String]) extends Question {
  override def toString: String = s"Question($alienValue)"
}

case class MetalQuestion(alienValue: List[String], name: String) extends Question {
  override def toString: String = s"Question($alienValue, $name)"
}

case class UnrecognisedQuestion(message: String) extends Question {
  override def toString: String = s"Question($message)"
}

case class AlienNumberAnswer(alienValue: List[String], price: Long) extends Answer

case class MetalAnswer(alienValue: List[String], name: String, price: Long) extends Answer

object Sentence {
  def groupBySentences(list: List[Sentence]): Map[String, List[Sentence]] = {
    list.groupBy(_.toString.split("\\(").head)
  }

  def sentencesToQuestions(list: List[Sentence]): Questions = {
    def go(remaining: List[Sentence], buff: Questions): Questions = remaining match {
      case List() => buff
      case h :: t => h match {
        case _: AlienNumberQuestion => go(t, buff ++ List(h))
        case _: MetalQuestion => go(t, buff ++ List(h))
        case _: UnrecognisedQuestion => go(t, buff ++ List(h))
        case _ => go(t, buff)
      }
    }

    go(list, List[Sentence]())
  }
}