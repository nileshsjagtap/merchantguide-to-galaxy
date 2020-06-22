package com.tw.core

import com.tw.domain.Error

trait Validate[I, A] {
  def validate(i: I): Either[Error, A]
}

object Validate {
  def apply[I, A](implicit v: Validate[I, A]): Validate[I, A] = v
}