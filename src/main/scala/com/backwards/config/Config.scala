package com.backwards.config

import com.backwards.mapping._

final case class Config(ftp: Ftp)

object Config {
  implicit val configMappable: Mappable[Config] =
    (m: Map[String, Map[String, Any]]) => Config(to[Ftp].from(m("ftp")).getOrElse(sys.error("Whoops")))
}

final case class Ftp(name: String, path: String, enabled: Boolean, temp: Int)