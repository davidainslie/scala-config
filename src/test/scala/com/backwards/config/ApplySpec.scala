package com.backwards.config

import cats.implicits.{catsSyntaxOptionId, none}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import com.backwards.config.Apply._

class ApplySpec extends AnyWordSpec with Matchers {
  "Apply" should {
    "provide ADT attribute access via 'apply' function" in {
      val common = Common(basic_size_limit = 10, student_size_limit = 11, paid_users_size_limit = 12, path = "some-path")
      val ftp = Ftp("ftp-name", "ftp-path", enabled = true)
      val http = Http("http-name", "http-path", List("array", "of", "values"))

      val config = Config(common, ftp, http)

      implicitly[Apply[Ftp]] mustBe an [Apply[_]]

      val name: Option[String] = config.ftp("name")
      name mustBe "ftp-name".some

      config.ftp("non-existing") mustBe none
    }
  }
}