package com.backwards.config.ini

import better.files.File
import cats.Id
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import com.backwards.config.Config
import com.backwards.config.ini.IniConfig.{parse, simplify}

class ConfigSpec extends AnyWordSpec with Matchers {
  /*"Config of ini" should {
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

    "parse a group of text" in {
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

    "parse a group of text ignoring comments" in {
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

    "parse groups of text" in {
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
  }*/

  "Config ini file" should {
    "be parsed into Map" in {

      //println("===> Ye baby: " + File("src/test/resources/test.ini").lines)
      println("===> Ye baby: " + parse(File("src/test/resources/test.ini").lines.toList))

      //val m = parse[Id]("src/test/resources/test.ini")

      //println(m)


      /*val v = parse(List("[ftp]", """name = "hello there, ftp uploading"""", "enabled = true"))

      println()
      println(v)*/
    }

    /*"...... be parsed into Map" in {
      val props = List(
        "[ftp]", """name = "ftp uploading"""", "enabled = true",
        "[http]", """name = "http blah blah"""", "canPost = on"
      )

      val v = parse(props)

      println()
      println(v)
    }*/
  }

  /*"Config" should {
    "" in {
      val blah = List(
        """[ftp]""" -> """""",
        """name""" -> """"hello there, ftp uploading"""",
        """path""" -> """/tmp/""",
        """path<production>""" -> """/srv/var/tmp/""",
        """path<staging>""" -> """/srv/uploads/""",
        """path<ubuntu>""" -> """/etc/var/uploads""",
        """enabled""" -> """no"""
      )


      val v = loadConfig[Config]("")
      println(v)
    }
  }*/
}

/*

[ftp]
name = "hello there, ftp uploading"
path = /tmp/
path<production> = /srv/var/tmp/
path<staging> = /srv/uploads/
path<ubuntu> = /etc/var/uploads
enabled = no


 */