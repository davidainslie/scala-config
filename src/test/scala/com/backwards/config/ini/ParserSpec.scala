package com.backwards.config.ini

import cats.Id
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import com.backwards.config.ini.Parser._

class ParserSpec extends AnyWordSpec with Matchers {
  "Config of ini" should {
    "parse a prop value" in {
      simplify("hello") mustBe "hello"
    }

    "parse a prop value in quotes" in {
      simplify(""" "hello" """) mustBe "hello"
      simplify(""" 'hello' """) mustBe "hello"
    }

    "parse a prop value removing comment" in {
      simplify("hello; comment") mustBe "hello"
    }

    "parse a prop value in quotes removing comment" in {
      simplify(""" "hello" ;comment """) mustBe "hello"
    }

    "parse a group of props" in {
      val props = List(
        "[ftp]", "name = ftp uploading", "enabled = true"
      )

      parse(props) mustBe
        Map(
          "ftp" -> Map(
            "name" -> "ftp uploading",
            "enabled" -> "true"
          )
        )
    }

    "parse a group of props ignoring comments" in {
      val props = List(
        "[ftp]", "name = ftp uploading", ";comment", "enabled = true", ";  comment"
      )

      parse(props) mustBe
        Map(
          "ftp" -> Map(
            "name" -> "ftp uploading",
            "enabled" -> "true"
          )
        )
    }

    "parse groups of props" in {
      val props = List(
        "[ftp]", """name = "ftp uploading"""", "enabled = true",
        "[http]", """name = "http blah blah"""", "canPost = on"
      )

      parse(props) mustBe
        Map(
          "ftp" -> Map(
            "name" -> "ftp uploading",
            "enabled" -> "true"
          ),
          "http" -> Map(
            "name" -> "http blah blah",
            "canPost" -> "on"
          )
        )
    }
  }

  "Config of ini with overrides" should {
    val props = List(
      "[ftp]", "name = ftp uploading", "enabled = true",
               "path = default path", "path<dev> = dev path", "path<prod> = prod path"
    )

    "parse a group of props that resolve to overrides default" in {
      parse(props) mustBe
        Map(
          "ftp" -> Map(
            "name" -> "ftp uploading",
            "enabled" -> "true",
            "path" -> "default path"
          )
        )
    }

    "parse a group of props that resolve to last given overrides" in {
      parse(props, "dev", "prod") mustBe
        Map(
          "ftp" -> Map(
            "name" -> "ftp uploading",
            "enabled" -> "true",
            "path" -> "prod path"
          )
        )
    }

    "parse a group of props that resolve to different last given overrides (per group)" in {
      val moreProps = props ++ List(
        "[common]", "path = default path", "path<itscript> = itscript path"
      )

      parse(moreProps, "dev", "itscript") mustBe
        Map(
          "common" -> Map(
            "path" -> "itscript path"
          ),
          "ftp" -> Map(
            "name" -> "ftp uploading",
            "enabled" -> "true",
            "path" -> "dev path"
          )
        )
    }
  }

  "Config ini file" should {
    "be parsed into Map" in {
      val m = parse[Id]("src/test/resources/test.ini")

      m("common") must not be empty
      m("ftp") must not be empty
      m("http") must not be empty
    }
  }
}