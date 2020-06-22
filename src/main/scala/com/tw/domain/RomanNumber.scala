package com.tw.domain

import com.tw.core.{Sequenceable, Validate}
import com.tw.implicits.AppImplicits._

case class RomanNumber private(numerals: List[RomanNumeral]) {

  def value = {
    def iterate(romanNumerals: List[RomanNumeral], buffer: Double): Double = romanNumerals match {
      case Nil => buffer
      case x :: Nil => x.value + buffer
      case x :: y :: xs if y <= x => iterate(y :: xs, x.value + buffer)
      case x :: y :: xs if x < y => iterate(xs, (y.value - x.value) + buffer)
      case _ => buffer
    }

    iterate(numerals, 0)
  }

}

case object RomanNumber extends Sequenceable {

  private def create(number: String) = for {
    numerals <- createNumerals(number)
    validNumerals <- validate(numerals)
  } yield new RomanNumber(validNumerals)

  private def createNumerals(number: String) = sequence(number.map(_.validate[RomanNumeral]).toList)

  private def validate(romanNumerals: List[RomanNumeral]) =
    allRomanNumberErrors.find(_.checkFor(romanNumerals.mkString(""))) match {
      case Some(error) => Left(error)
      case None => Right(romanNumerals)
    }

  private def allRomanNumberErrors = List(
    MoreThanThreeI,
    MoreThanThreeX,
    MoreThanThreeC,
    MoreThanThreeM,
    MoreThanOneV,
    MoreThanOneL,
    MoreThanOneD,
    InvalidISubtractions,
    InvalidXSubtractions
  )

  def createFromNumerals(romanNumerals: List[RomanNumeral]) = validate(romanNumerals).map(new RomanNumber(_))

  implicit def validateRomanNumber = new Validate[String, RomanNumber] {
    override def validate(number: String) =
      checkForRomanNumberError(number).flatMap(checkForRomanNumeralError)

    def checkForRomanNumberError(number: String) =
      allRomanNumberErrors.find(_.checkFor(number)) match {
        case Some(error) => Left(error)
        case None => Right(number)
      }

    def checkForRomanNumeralError(number: String) = sequence(number.map(_.validate[RomanNumeral]).toList).map(RomanNumber(_))
  }

}