package com.backwards.config.ini

import cats.data.OptionT
import cats.effect.IO
import cats.implicits._
import com.backwards.config.ini.Parser.parse

object Config {
  /**
   * Instantiate some configuration ADT represented here by C.
   * Note that the loading (from some path) is performed within the context of IO Monad simply to highlight the side-effect of interacting with an external system.
   *
   * NOTE - Loading of configuration should be at "end of the world", and as per requirement, a side-effecting error can be raised if loading of configuration failed.
   */
  def loadConfig[C: Mappable](path: String, overrides: String*): IO[C] = {
    OptionT(parse[IO](path).map(Mappable[C].map))
      .getOrElseF(IO raiseError new Exception(s"Failed to load ini $path into requested ADT"))
  }
}