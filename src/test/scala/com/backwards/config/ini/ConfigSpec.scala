package com.backwards.config.ini

import cats.Id
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import com.backwards.config.Config
import com.backwards.config.ini.Config.loadConfig

class ConfigSpec extends AnyWordSpec with Matchers {
  "Config" should {
    "" in {
      val v = loadConfig[Id, Config]("src/test/resources/test.ini")
      println(v)

    }
  }
}