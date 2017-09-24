package com.tw.intergalactic.violations

import com.tw.intergalactic.core.RomanNumber.RomanNumber

sealed trait RomanNumberViolation extends Violation {
  val next: RomanNumberViolation

  def checkFor(roman: List[RomanNumber]): Boolean
}

case object MoreThanThreeI extends RomanNumberViolation {
  override val next: RomanNumberViolation = MoreThanThreeX

  override def checkFor(roman: List[RomanNumber]): Boolean = roman.mkString("").contains("IIII")
}

case object MoreThanThreeX extends RomanNumberViolation {
  override val next: RomanNumberViolation = MoreThanThreeC

  override def checkFor(roman: List[RomanNumber]): Boolean = roman.mkString("").contains("XXXX")
}

case object MoreThanThreeC extends RomanNumberViolation {
  override val next: RomanNumberViolation = MoreThanThreeM

  override def checkFor(roman: List[RomanNumber]): Boolean = roman.mkString("").contains("CCCC")
}

case object MoreThanThreeM extends RomanNumberViolation {
  override val next: RomanNumberViolation = MoreThanOneV

  override def checkFor(roman: List[RomanNumber]): Boolean = roman.mkString("").contains("MMMM")
}

case object MoreThanOneV extends RomanNumberViolation {
  override val next: RomanNumberViolation = MoreThanOneL

  override def checkFor(roman: List[RomanNumber]): Boolean = roman.mkString("").contains("VV")
}

case object MoreThanOneL extends RomanNumberViolation {
  override val next: RomanNumberViolation = MoreThanOneD

  override def checkFor(roman: List[RomanNumber]): Boolean = roman.mkString("").contains("LL")
}

case object MoreThanOneD extends RomanNumberViolation {
  override val next: RomanNumberViolation = InvalidISubtractions

  override def checkFor(roman: List[RomanNumber]): Boolean = roman.mkString("").contains("DD")
}

case object InvalidISubtractions extends RomanNumberViolation {
  override val next: RomanNumberViolation = InvalidXSubtractions

  override def checkFor(roman: List[RomanNumber]): Boolean = {
    val romanString = roman.mkString("")
    romanString.contains("IL") || romanString.contains("IC") || romanString.contains("ID") || romanString.contains("IM")
  }
}

case object InvalidXSubtractions extends RomanNumberViolation {
  override val next: RomanNumberViolation = InvalidXSubtractions

  override def checkFor(roman: List[RomanNumber]): Boolean = {
    val romanString = roman.mkString("")
    romanString.contains("XD") || romanString.contains("XM")
  }
}