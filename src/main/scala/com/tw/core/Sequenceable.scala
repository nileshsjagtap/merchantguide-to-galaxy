package com.tw.core

trait Sequenceable {
  def sequence[B, A](eithers: List[Either[B, A]]): Either[B, List[A]] = {
    def iterate(remaining: List[Either[B, A]], buffer: Either[B, List[A]]): Either[B, List[A]] =
      remaining match {
        case Nil => buffer
        case head :: _ if head.isLeft => Left(head.left.get)
        case head :: tail => iterate(tail, Right(buffer.right.get :+ head.right.get))
      }

    iterate(eithers, Right(List.empty[A]))
  }
}