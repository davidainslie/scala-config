package com.backwards

import shapeless.labelled.{FieldType, field}
import shapeless.{::, HList, HNil, LabelledGeneric, Typeable, Witness}

package object mapping {
  trait Mappable[A] {
    def map(m: Map[String, Map[String, Any]]): A
  }

  object Mappable {
    def apply[A: Mappable]: Mappable[A] = implicitly[Mappable[A]]
  }

  /*trait Mapping[A] {
    def cast[V](typeable: Typeable[V], v: V)
  }*/

  trait FromMap[L <: HList] {
    def apply(m: Map[String, Any]): Option[L]
  }

  object FromMap {
    implicit val hnil: FromMap[HNil] =
      _ => Some(HNil)

    implicit def hlist[K <: Symbol, V, T <: HList](implicit witness: Witness.Aux[K], typeable: Typeable[V], fromMap: FromMap[T]): FromMap[FieldType[K, V] :: T] =
      (m: Map[String, Any]) => for {
        v <- m.get(witness.value.name)
        r <- typeable.cast(v)
        t <- fromMap(m)
      } yield field[K][V](r) :: t
  }

  class ToMap[A/*: Mapping*/] {
    def from[R <: HList](m: Map[String, Any])(implicit generic: LabelledGeneric.Aux[A, R], fromMap: FromMap[R]): Option[A] =
      fromMap(m).map(generic.from)
  }

  def to[A/*: Mapping*/] = new ToMap[A]
}