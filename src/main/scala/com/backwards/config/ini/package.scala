package com.backwards.config

import cats.Monad
import com.backwards.mapping.Mappable

package object ini {
  def loadConfig[F[_]: Monad, C: Mappable](path: String, overrides: String*): F[C] = {
    Monad[F].map(IniConfig.parse(path)) { m =>
      Mappable[C].map(m)
    }
  }
}