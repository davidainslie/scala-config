package com.backwards.config.ini

import cats.implicits._
import shapeless.{HList, LabelledGeneric}

class Mapping[A] {
  def from[R <: HList](m: Map[String, Any])(implicit generic: LabelledGeneric.Aux[A, R], fromMap: FromMap[R]): Option[A] =
    fromMap(m).map(generic.from)
}

object Mapping {
  def to[A] = new Mapping[A]
}