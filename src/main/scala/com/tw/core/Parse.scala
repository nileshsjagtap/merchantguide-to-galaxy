package com.tw.core

import com.tw.domain.Error

trait Parse[I, A] {
  def parse(i: I): Either[Error, A]
}

object Parse {
  def apply[I, A](implicit p: Parse[I, A]): Parse[I, A] = p
}