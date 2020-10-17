package com.backwards.config.ini

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import com.backwards.config.Config
import com.backwards.config.ini.IniConfig.parse

class ConfigSpec extends AnyWordSpec with Matchers {
  "Config" should {
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
  }

  "Config file" should {
    "be parsed into Map" ignore {
      val v = parse(List("[ftp]", """name = "hello there, ftp uploading"""", "enabled = true"))

      println()
      println(v)
    }

    "...... be parsed into Map" in {
      val props = List(
        "[ftp]", """name = "ftp uploading"""", "enabled = true",
        "[http]", """name = "http blah blah"""", "canPost = on"
      )

      val v = parse(props)

      println()
      println(v)
    }
  }
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