package com.tw.domain

import com.tw.core.{Sequenceable, Validate}
import com.tw.implicits.AppImplicits._

case class Metals private(metals: List[Metal]) {
  def get(metalName: String) = metals.filter(_.name == metalName) match {
    case metal :: Nil => Right(metal)
    case _ => Left(InvalidMetalName)
  }
}

object Metals extends Sequenceable {
  implicit def validateMetals = new Validate[MetalsInput, Metals] {
    override def validate(metalsInput: MetalsInput) =
      sequence(metalsInput.sentences.map(MetalInput(_, metalsInput.alienNumbers)).map(_.validate[Metal])).map(Metals(_))
  }
}

case class MetalsInput(sentences: List[Sentence], alienNumbers: AlienNumbers)