package com.backwards.config

import cats.Monad
import com.backwards.mapping.Mappable
import cats.implicits._

package object ini {


  /*def loadConfig[F[_]: Monad, C: Mappable](path: String, overrides: String*): C =
    IniConfig.parse(path).map { m =>
      Mappable[C].map(m)
    }*/

  def loadConfig[C: Mappable](path: String, overrides: String*): C =
    Mappable[C].map(IniConfig.parseX(path))
}