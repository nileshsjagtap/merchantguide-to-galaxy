package com.tw.core

trait LessThanEqual[A] {
  def <=(left: A, right: A): Boolean

  def transitiveLaw(a1: A, a2: A, a3: A): Boolean =
    (<=(a1, a2) && <=(a2, a3)) == <=(a1, a3)
}

object LessThanEqual {
  def apply[A](implicit a: LessThanEqual[A]): LessThanEqual[A] = a
}
