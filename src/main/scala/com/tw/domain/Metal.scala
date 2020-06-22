package com.tw.domain

import com.tw.core.Validate

case class Metal private(name: String, perUnitValue: Double)

object Metal {
  private def invalidMetalName(statement: MetalStatement) = statement.metalName.isEmpty

  private def invalidMetalPrice(statement: MetalStatement) = statement.totalPrice == 0.0

  private def emptyAlienTokens(statement: MetalStatement) = statement.alienTokens.count(_ == "") > 0

  private def invalidAlienTokens(statement: MetalStatement, alienNumbers: AlienNumbers) = !alienNumbers.validTokens(statement.alienTokens)

  private def createMetalFrom(statement: MetalStatement, alienNumbers: AlienNumbers) =
    for {
      romanNumber <- createRomanNumber(statement, alienNumbers)
    } yield Metal(statement.metalName, statement.totalPrice / romanNumber.value)

  private def createRomanNumber(statement: MetalStatement, alienNumbers: AlienNumbers) =
    alienNumbers.romanNumeralsFor(statement.alienTokens).flatMap(RomanNumber.createFromNumerals)

  implicit def validateMetal = new Validate[MetalInput, Metal] {
    override def validate(metalInput: MetalInput) = metalInput.sentence match {
      case statement: MetalStatement if invalidMetalName(statement) => Left(InvalidMetalName)
      case statement: MetalStatement if invalidMetalPrice(statement) => Left(InvalidMetalPrice)
      case statement: MetalStatement if emptyAlienTokens(statement) => Left(InvalidAlienTokens)
      case statement: MetalStatement if invalidAlienTokens(statement, metalInput.alienNumbers) => Left(InvalidAlienTokens)
      case statement: MetalStatement => createMetalFrom(statement, metalInput.alienNumbers)
      case _ => Left(InvalidMetalStatement)
    }
  }

}

case class MetalInput(sentence: Sentence, alienNumbers: AlienNumbers)