package com.tw.domain

sealed abstract class Error

case object InvalidNumeral extends Error
case object InvalidAlienNumber extends Error
case object InvalidMetalName extends Error
case object InvalidMetalPrice extends Error
case object InvalidAlienTokens extends Error
case object InvalidAlienNumberStatement extends Error
case object InvalidAlienNumberQuestion extends Error
case object InvalidMetalStatement extends Error
case object InvalidMetalQuestion extends Error
case object InvalidQuestion extends Error
case object ErrorWritingToFile extends Error
case object ErrorReadingFromFile extends Error


sealed abstract class RomanNumberError extends Error {
  def checkFor(romanNumerals: String): Boolean
}

case object MoreThanThreeI extends RomanNumberError {
  override def checkFor(romanNumerals: String): Boolean = romanNumerals.contains("IIII")
}

case object MoreThanThreeX extends RomanNumberError {
  override def checkFor(romanNumerals: String): Boolean = romanNumerals.contains("XXXX")
}

case object MoreThanThreeC extends RomanNumberError {
  override def checkFor(romanNumerals: String): Boolean = romanNumerals.contains("CCCC")
}

case object MoreThanThreeM extends RomanNumberError {
  override def checkFor(romanNumerals: String): Boolean = romanNumerals.contains("MMMM")
}

case object MoreThanOneV extends RomanNumberError {
  override def checkFor(romanNumerals: String): Boolean = romanNumerals.contains("VV")
}

case object MoreThanOneL extends RomanNumberError {
  override def checkFor(romanNumerals: String): Boolean = romanNumerals.contains("LL")
}

case object MoreThanOneD extends RomanNumberError {
  override def checkFor(romanNumerals: String): Boolean = romanNumerals.contains("DD")
}

case object InvalidISubtractions extends RomanNumberError {
  override def checkFor(romanNumerals: String): Boolean =
    romanNumerals.contains("IL") ||
      romanNumerals.contains("IC") ||
      romanNumerals.contains("ID") ||
      romanNumerals.contains("IM")
}

case object InvalidXSubtractions extends RomanNumberError {
  override def checkFor(romanNumerals: String): Boolean =
    romanNumerals.contains("XD") || romanNumerals.contains("XM")
}