package com.tw.domain

import com.tw.core.Validate
import com.tw.implicits.AppImplicits._

case class AlienNumber private(token: String, romanNumeral: RomanNumeral)

object AlienNumber {
  implicit def validateAlienNumber = new Validate[Sentence, AlienNumber] {
    override def validate(sentence: Sentence) = sentence match {
      case statement: AlienNumberStatement if statement.alienNumber.isEmpty => Left(InvalidAlienNumber)
      case statement: AlienNumberStatement =>
        statement.romanNumeral.headOption.getOrElse('_').validate[RomanNumeral]
        .map(AlienNumber(statement.alienNumber, _))
      case _ => Left(InvalidAlienNumberStatement)
    }
  }
}