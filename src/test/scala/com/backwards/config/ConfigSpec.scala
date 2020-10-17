package com.backwards.config

import cats.implicits._
import shapeless._
import shapeless.labelled.FieldType
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

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

  // TODO - Might have wanted to include typeable: Typeable[V]
  implicit def hlistApply[K <: Symbol, H, T <: HList](implicit witness: Witness.Aux[K], applyH: Lazy[Apply[H]], applyT: Apply[T]): Apply[FieldType[K, H] :: T] =
    new Apply[FieldType[K, H] :: T] {
      def apply(key: String, a: FieldType[K, H] :: T): Option[V] = {

        //println("> " + a.head)
        //println("===> Witness = " + witness.value.name)

        if (key == witness.value.name) a.head.asInstanceOf[V].some
        else applyT.apply(key, a.tail).map(_.asInstanceOf[V])

        //None
      }
    }

  /* Generic.Aux[A,R] is shorthand for: Generic[A] { type Repr = R } */
  implicit def genericApply[A, R <: HList](implicit generic: LabelledGeneric.Aux[A, R], applyR: Lazy[Apply[R]]): Apply[A] =
    new Apply[A] {
      def apply(key: String, a: A): Option[V] = {
        //println(s"===> a = $a")
        applyR.value.apply(key, generic.to(a)).map(_.asInstanceOf[V])
      }
    }

  implicit class ApplyOps[A: Apply](a: A) {
    def apply[V](key: String): Option[V] = implicitly[Apply[A]].apply(key, a).map(_.asInstanceOf[V]) // TODO - Don't like
  }
}

class ConfigSpec extends AnyWordSpec with Matchers {
  "4" should {
    "" in {
      val common = Common(basic_size_limit = 10, student_size_limit = 11, paid_users_size_limit = 12, path = "some-path")
      val ftp = Ftp("my-name", "my-path", true)
      val config = Config(common, ftp)

      import Apply._

      //println(config.ftp(""))
      //println(config.ftp.apply("name"))

      //println(implicitly[Foo[Ftp]])


      implicitly[Apply[Ftp]]

      val name: Option[String] = ftp("name")
      println(name.get)

      val path: Option[String] = ftp[String]("path")

      val enabled = ftp("enabled")

      val nonExisting: Option[Int] = ftp("non-existing")
      println(nonExisting)
    }
  }
}