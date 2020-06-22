package com.tw.core

trait LessThan[A] {
  def <(left: A, right: A): Boolean

  def transitiveLaw(a1: A, a2: A, a3: A): Boolean =
    (<(a1, a2) && <(a2, a3)) == <(a1, a3)
}

object LessThan {
  def apply[A](implicit a: LessThan[A]): LessThan[A] = a
}