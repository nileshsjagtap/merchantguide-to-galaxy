package com.tw.intergalactic.core

import com.tw.intergalactic.core.RomanNumber._
import com.tw.intergalactic.violations._
import org.scalatest.{FunSpec, Matchers}

class RomanNumberSpec extends FunSpec with Matchers {

  describe("Roman Number ") {

    it("should give corresponding number in case ") {
      //given
      val roman = List(M, C, M, X, L, I, V)

      //when
      val value: Either[Violation, Long] = RomanNumber.eval(roman)

      //then
      value.right.get shouldEqual 1944
    }
    it("should give error in case of repeated roman literals") {
      //given
      val romanI = List(I, I, I, I)
      val romanX = List(X, X, X, X)
      val romanC = List(C, C, C, C)
      val romanM = List(M, M, M, M)
      val romanV = List(V, V, V, V)
      val romanL = List(L, L, L, L)
      val romanD = List(D, D, D, D)

      //when
      val valueI = RomanNumber.eval(romanI).left.get
      val valueX = RomanNumber.eval(romanX).left.get
      val valueC = RomanNumber.eval(romanC).left.get
      val valueM = RomanNumber.eval(romanM).left.get
      val valueV = RomanNumber.eval(romanV).left.get
      val valueL = RomanNumber.eval(romanL).left.get
      val valueD = RomanNumber.eval(romanD).left.get

      //then
      valueI shouldEqual MoreThanThreeI
      valueX shouldEqual MoreThanThreeX
      valueC shouldEqual MoreThanThreeC
      valueM shouldEqual MoreThanThreeM
      valueV shouldEqual MoreThanOneV
      valueL shouldEqual MoreThanOneL
      valueD shouldEqual MoreThanOneD
    }
    it("should give error in case of invalid subtractions ") {
      //given
      val romanI = List(I, I, L, I)
      val romanX = List(X, D, L, I)

      //when
      val valueI = RomanNumber.eval(romanI).left.get
      val valueX = RomanNumber.eval(romanX).left.get

      //then
      valueI shouldEqual InvalidISubtractions
      valueX shouldEqual InvalidXSubtractions
    }

  }

}
