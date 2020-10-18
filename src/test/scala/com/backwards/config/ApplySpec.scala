package com.backwards.config

import cats.implicits.{catsSyntaxOptionId, none}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import com.backwards.config.Apply._

class ApplySpec extends AnyWordSpec with Matchers {
  "Apply" should {
    "provide ADT attribute access via 'apply' function" in {
      val common = Common(basic_size_limit = 10, student_size_limit = 11, paid_users_size_limit = 12, path = "some-path")
      val ftp = Ftp("my-name", "my-path", enabled = true)
      val config = Config(common, ftp)

      implicitly[Apply[Ftp]] mustBe an [Apply[_]]

      val name: Option[String] = config.ftp("name")
      name mustBe "my-name".some

      config.ftp("non-existing") mustBe none
    }
  }
}