package com.tw.core

import com.tw.domain.{AlienNumberAnswer, MetalInfoAnswer, Sentence, UnrecognisedQuestionAnswer}
import com.tw.implicits.AppImplicits._

trait Show[A] {
  def show(a: A): String
}

object Show {
  def apply[A](implicit showOfa: Show[A]) = showOfa

  implicit def sentenceShow = new Show[Sentence] {
    override def show(sentence: Sentence): String = sentence match {
      case answer: AlienNumberAnswer => answer.show
      case answer: MetalInfoAnswer => answer.show
      case answer: UnrecognisedQuestionAnswer => answer.show
      case _ => ""
    }
  }
}
