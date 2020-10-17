package com.backwards.config

import cats.Monad
import com.backwards.mapping.Mappable

package object ini {
  /**
   * Instantiate some configuration ADT represented here by C.
   * Note that the loading (from some path) is performed within the context of a Monad simply to highlight the side-effect of interacting with an external system.
   */
  def loadConfig[F[_]: Monad, C: Mappable](path: String, overrides: String*): F[C] =
    Monad[F].map(Config.parse(path)) { m =>
      Mappable[C].map(m)
    }
}