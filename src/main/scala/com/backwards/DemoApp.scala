package com.backwards

import cats.Id
import cats.effect.{ExitCode, IO, IOApp}
import com.backwards.config.Config
import com.backwards.config.ini.Config.loadConfig

object DemoApp extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {

    val v = loadConfig[Id, Config]("src/test/resources/test.ini")
    println(v)

    ???
  }
}