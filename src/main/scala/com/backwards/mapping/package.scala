package com.backwards

import scala.util.Try
import cats.implicits._
import shapeless.labelled.{FieldType, field}
import shapeless.{::, HList, HNil, LabelledGeneric, Typeable, Witness}
import com.backwards.config.BooleanConfig

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
        /**
         * TODO - Didn't expect to end up doing this nasty "cast" - The following follows [[shapeless.Typeable]]
         */
        def cast(v: Any): Option[V] = {
          def cast[O](o: Option[O]): Option[V] =
            o.fold(typeable.cast(v))(v => typeable.cast(v.asInstanceOf[V]))

          typeable.describe match {
            case "Byte" => cast(Try(v.toString.toByte).toOption)
            case "Short" => cast(Try(v.toString.toShort).toOption)
            case "Char" => cast(Try(v.toString.charAt(0)).toOption)
            case "Int" => cast(Try(v.toString.toInt).toOption)
            case "Long" => cast(Try(v.toString.toLong).toOption)
            case "Float" => cast(Try(v.toString.toFloat).toOption)
            case "Double" => cast(Try(v.toString.toDouble).toOption)
            case "Boolean" => cast(BooleanConfig.boolean(v))
            case _ => typeable.cast(v)
          }
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