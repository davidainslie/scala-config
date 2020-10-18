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
      config <- loadConfig[Config]("src/test/resources/test.ini")
      _ <- logger info green(s"Configuration loaded: $config")
      _ <- logger info blue("Access config attributes as normal:")
      _ <- logger info green(s"FTP name: ${config.ftp.name}")
      _ <- logger info blue("Or with Apply functionality imported, access config attributes via named attribute:")
      _ <- logger info green(s"FTP name via named attribute: ${config.ftp("name")}")
    } yield ExitCode.Success
}