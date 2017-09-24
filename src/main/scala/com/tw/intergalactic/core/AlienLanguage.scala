package com.tw.intergalactic.core

import com.tw.intergalactic.core.RomanNumber.RomanNumber

case class AlienLanguage(list: List[AlienNumberStatement]) {

  val alienToRomanNumberMap: Map[String, RomanNumber] = createAlienToRomanNumberMap()

  private def createAlienToRomanNumberMap(): Map[String, RomanNumber] = {
    list.map(s => (s.name, RomanNumber.createFromString(s.romanNumeral))).toMap
  }

  def contains(string: String): Boolean = alienToRomanNumberMap.contains(string)

  def validAlienTokens(strings: List[String]): Boolean = {
    def iterate(strings: List[String]): Boolean = strings match {
      case List() => true
      case x :: xs if contains(x) => iterate(xs)
      case _ => false
    }
    iterate(strings)
  }

  def stringToRomanNumber(string: String): RomanNumber = alienToRomanNumberMap(string)

  def stringsToRomanNumberList(strings: List[String]): List[RomanNumber] = strings.map(stringToRomanNumber)

}