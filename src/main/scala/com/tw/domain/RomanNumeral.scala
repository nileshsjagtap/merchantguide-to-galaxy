package com.tw.domain

import com.tw.core.{LessThan, LessThanEqual, Validate}

sealed abstract class RomanNumeral (val value: Int)

case object I extends RomanNumeral(1)
case object V extends RomanNumeral(5)
case object X extends RomanNumeral(10)
case object L extends RomanNumeral(50)
case object C extends RomanNumeral(100)
case object D extends RomanNumeral(500)
case object M extends RomanNumeral(1000)

object RomanNumeral {

  implicit def lessThanRomanNumeral = new LessThan[RomanNumeral] {
    override def <(left: RomanNumeral, right: RomanNumeral): Boolean = left.value < right.value
  }

  implicit def lessThanEqualRomanNumeral = new LessThanEqual[RomanNumeral] {
    override def <=(left: RomanNumeral, right: RomanNumeral): Boolean = left.value <= right.value
  }

  implicit def validateRomanNumeral = new Validate[Char, RomanNumeral] {
    override def validate(numeral: Char): Either[Error, RomanNumeral] = numeral match {
      case 'I' => Right(I)
      case 'V' => Right(V)
      case 'X' => Right(X)
      case 'L' => Right(L)
      case 'C' => Right(C)
      case 'D' => Right(D)
      case 'M' => Right(M)
      case _ => Left(InvalidNumeral)
    }
  }

}