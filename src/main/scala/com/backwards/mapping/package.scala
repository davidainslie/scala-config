package com.backwards

import cats.implicits._
import shapeless.labelled.{FieldType, field}
import shapeless.{::, HList, HNil, LabelledGeneric, Typeable, Witness}
import com.backwards.config.BooleanType

package object mapping {
  trait Mappable[A] {
    def map(m: Map[String, Map[String, Any]]): A
  }

  object Mappable {
    def apply[A: Mappable]: Mappable[A] = implicitly[Mappable[A]]
  }

  trait FromMap[L <: HList] {
    def apply(m: Map[String, Any]): Option[L]
  }

  object FromMap {
    implicit val hnil: FromMap[HNil] =
      _ => Some(HNil)

    implicit def hlist[K <: Symbol, V, T <: HList](implicit witness: Witness.Aux[K], typeable: Typeable[V], fromMap: FromMap[T]): FromMap[FieldType[K, V] :: T] =
      (m: Map[String, Any]) => {
        def cast(v: Any): Option[V] =
          if (typeable.describe == "Boolean") v match {
            case BooleanType(b) => typeable.cast(b)
            case _ => typeable.cast(v)
          } else {
            typeable.cast(v)
          }

        for {
          v <- m.get(witness.value.name)
          r <- cast(v)
          t <- fromMap(m)
        } yield field[K][V](r) :: t
      }
  }

  class ToMap[A] {
    def from[R <: HList](m: Map[String, Any])(implicit generic: LabelledGeneric.Aux[A, R], fromMap: FromMap[R]): Option[A] =
      fromMap(m).map(generic.from)
  }

  def to[A] = new ToMap[A]
}