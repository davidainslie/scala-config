package com.backwards.config

import cats.implicits._
import shapeless._
import shapeless.labelled.FieldType

trait Apply[A] {
  type V

  def apply(key: String, a: A): Option[V]
}

object Apply {
  implicit val stringApply: Apply[String] = new Apply[String] {
    type V = String

    def apply(key: String, a: String): Option[V] = a.some
  }

  implicit val intApply: Apply[Int] = new Apply[Int] {
    type V = Int

    def apply(key: String, a: Int): Option[V] = a.some
  }

  implicit val doubleApply: Apply[Double] = new Apply[Double] {
    type V = Double

    def apply(key: String, a: Double): Option[V] = a.some
  }

  implicit val booleanApply: Apply[Boolean] = new Apply[Boolean] {
    type V = Boolean

    def apply(key: String, a: Boolean): Option[V] = a.some
  }

  implicit val hnilApply: Apply[HNil] = new Apply[HNil] {
    type V = Nothing

    def apply(key: String, a: HNil): Option[V] =
      None
  }

  implicit def hlistApply[K <: Symbol, H, T <: HList](implicit witness: Witness.Aux[K], applyH: Lazy[Apply[H]], applyT: Apply[T]): Apply[FieldType[K, H] :: T] =
    new Apply[FieldType[K, H] :: T] {
      def apply(key: String, a: FieldType[K, H] :: T): Option[V] =
        if (key == witness.value.name) a.head.asInstanceOf[V].some
        else applyT.apply(key, a.tail).map(_.asInstanceOf[V])
    }

  implicit def genericApply[A, R <: HList](implicit generic: LabelledGeneric.Aux[A, R], applyR: Lazy[Apply[R]]): Apply[A] =
    new Apply[A] {
      def apply(key: String, a: A): Option[V] =
        applyR.value.apply(key, generic.to(a)).map(_.asInstanceOf[V])
    }

  implicit class ApplyOps[A: Apply](a: A) {
    def apply[V](key: String): Option[V] = implicitly[Apply[A]].apply(key, a).map(_.asInstanceOf[V])
  }
}