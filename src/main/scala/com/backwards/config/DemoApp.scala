package com.backwards.config

import cats.effect.{ExitCode, IO, IOApp}
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import com.backwards.config.Apply._
import com.backwards.config.Console._
import com.backwards.config.ini.Config.loadConfig

object DemoApp extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    for {
      logger <- Slf4jLogger.create[IO]
      config <- loadConfig[Config]("src/test/resources/test.ini", "staging", "itscript")
      _ <- logger info
        green(s"Configuration loaded: $config") +
        blue("\nAccess config attributes as normal:") +
        green(s"\nFTP name: ${config.ftp.name}") +
        blue("\nOr with Apply functionality imported, access config attributes via named attribute:") +
        green(s"\nFTP name via named attribute: ${config.ftp("name")}")
    } yield ExitCode.Success
}