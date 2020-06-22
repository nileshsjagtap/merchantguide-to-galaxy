import com.tw.domain.InvalidNumeral

sealed abstract class RomanNumerall (val value: Int)

case object I extends RomanNumerall(1)
case object V extends RomanNumerall(5)
case object X extends RomanNumerall(10)
case object L extends RomanNumerall(50)
case object C extends RomanNumerall(100)
case object D extends RomanNumerall(500)
case object M extends RomanNumerall(1000)

object RomanNumerall {

  def apply(numeral: String) = numeral match {
    case "I" => Right(I)
    case "V" => Right(V)
    case "X" => Right(X)
    case "L" => Right(L)
    case "C" => Right(C)
    case "D" => Right(D)
    case "M" => Right(M)
    case _ => Left(InvalidNumeral)
  }

  def apply(numeral: Char) = numeral match {
    case 'I' => Right(I)
    case 'V' => Right(V)
    case 'X' => Right(X)
    case 'L' => Right(L)
    case 'C' => Right(C)
    case 'D' => Right(D)
    case 'M' => Right(M)
    case _ => Left(InvalidNumeral)
  }

  val i: RomanNumerall = I
  val v: RomanNumerall = V

  lazy val some = LT.RNLT.<(i, v)

  lazy val some1 = implicitly[LT[RomanNumerall]].<(i, v)

  lazy val some2 = LT[RomanNumerall].<(i, v)

  import Syntax._
  lazy val some3 = i < v

}

trait LT[A] {
  def <(a1: A, a2: A): Boolean

  def transitivity(a1: A, a2: A, a3: A) =
    <(a1, a2) && <(a2, a3) == <(a1, a3)
}

object LT {

  def apply[A](implicit lt: LT[A]) = lt

  implicit def RNLT = new LT[RomanNumerall] {
    override def <(a1: RomanNumerall, a2: RomanNumerall): Boolean = a1.value < a2.value
  }

}

object Syntax {

  implicit class RNLTSyntax[A](`this`: A) {
    def <(that: A)(implicit lt: LT[A]): Boolean = lt < (`this`, that)
  }

}

RomanNumerall.some

RomanNumerall.some1

RomanNumerall.some2

RomanNumerall.some3

trait Semigroup[T] {
  def |+|(t1: T, t2: T): T

  def law(t1: T, t2: T) =
    |+|(t1, t2) == |+|(t2, t1)
}

object Semigroup {

  def apply[A](implicit lt: Semigroup[A]) = lt

  implicit def RNLT1 = new Semigroup[Int] {
    override def |+|(a1: Int, a2: Int): Int = a1 + a2
  }

}

val list = List(1,2,3,4)

list.foldLeft(0)(implicitly[Semigroup[Int]].|+|)

trait Monoid[A] extends Semigroup[A] {

  def zero: A

  def laws = ???
}


object Monoid {

  def apply[A](implicit lt: Monoid[A]) = lt

  implicit def RNLT1 = new Monoid[Int] {
    override def |+|(a1: Int, a2: Int): Int = a1 + a2
    override def zero: Int = 0
  }

}

list.foldLeft(Monoid[Int].zero)(Monoid[Int].|+|)







trait Fruit

case class Apple(nutriValue: Int) extends Fruit
case class Orange(nutriValue: Int) extends Fruit
case class Banana(nutriValue: Int) extends Fruit

trait Show[A] {
  def show(a: A): String
}

object Show {

  def apply[A](implicit showOfA: Show[A]) = showOfA

//  lazy val myApplle = implicitly[Show[Apple]].show(Apple(2))

  implicit def fruitShow = new Show[Fruit] {
    override def show(fruit: Fruit): String = fruit match {
      case f: Apple => appleShow.show(f)
      case f: Orange => orangeShow.show(f)
      case f: Banana => bananaShow.show(f)
    }
  }

  implicit def appleShow = new Show[Apple] {
    override def show(apple: Apple): String = s"Apple, nutriValue:${apple.nutriValue}"
  }

  implicit def orangeShow = new Show[Orange] {
    override def show(orange: Orange): String = s"Orange, nutriValue:${orange.nutriValue}"
  }

  implicit def bananaShow = new Show[Banana] {
    override def show(banana: Banana): String = s"Banana, nutriValue:${banana.nutriValue}"
  }

}

object Syntaxx {

  implicit class ShowSyntax[A](a: A) {
    def show(implicit show: Show[A]) = show.show(a)
  }

}

val fruit: Fruit = Orange(5)

import Syntaxx._
Apple(2).show
Orange(3).show
Banana(4).show
fruit.show