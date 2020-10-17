package com.backwards.config

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class Config6Spec extends AnyWordSpec with Matchers {
  "" should {
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