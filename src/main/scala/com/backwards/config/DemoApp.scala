package com.backwards.config

import cats.effect.{ExitCode, IO, IOApp}
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import com.backwards.config.Console._
import com.backwards.config.ini.Config.loadConfig

object DemoApp extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    for {
      logger <- Slf4jLogger.create[IO]
      config <- loadConfig[Config]("src/test/resources/test.ini")
      _ <- logger info green(s"Configuration loaded: $config")
    } yield ExitCode.Success
}