package com.tw.core.syntax

import com.tw.core._
import com.tw.domain.Error

trait Syntax {

  implicit class LessThanSyntax[A](left: A) {
    def <(right: A)(implicit lt: LessThan[A]): Boolean = lt < (left, right)
  }

  implicit class LessThanEqualSyntax[A](left: A) {
    def <=(right: A)(implicit lte: LessThanEqual[A]): Boolean = lte <= (left, right)
  }

  implicit class ShowSyntax[A](a: A) {
    def show(implicit show: Show[A]): String = show.show(a)
  }

  implicit class ValidateSyntax[I](i: I) {
    def validate[A](implicit validate: Validate[I, A]): Either[Error, A] = validate.validate(i)
  }

  implicit class ParseSyntax[I](i: I) {
    def parse[A](implicit parse: Parse[I, A]): Either[Error, A] = parse.parse(i)
  }

}