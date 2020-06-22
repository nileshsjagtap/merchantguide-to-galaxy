package com.tw.domain

import com.tw.core.{Sequenceable, Validate}
import com.tw.implicits.AppImplicits._

case class AlienNumbers private(alienNumbers: List[AlienNumber]) {
  def validTokens(tokenList: List[String]) = tokenList.forall(tokens.contains(_))

  def tokens = alienNumbers.map(_.token)

  def romanNumeralsFor(alienTokens: List[String]): Either[InvalidAlienTokens.type, List[RomanNumeral]] =
    if (!validTokens(alienTokens)) Left(InvalidAlienTokens)
    else Right(
      alienTokens.flatMap(at => alienNumbers.filter(_.token == at))
        .map(_.romanNumeral))
}

object AlienNumbers extends Sequenceable {
  implicit def validateAlienNumbers = new Validate[List[Sentence], AlienNumbers] {
    override def validate(sentences: List[Sentence]) =
      sequence(sentences.map(_.validate[AlienNumber])).map(AlienNumbers(_))
  }
}