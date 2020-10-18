package com.backwards.config

import cats.implicits.catsSyntaxTuple2Semigroupal
import com.backwards.config.ini.Mappable
import com.backwards.config.ini.Mapping.to

final case class Config(common: Common, ftp: Ftp)

object Config {
  implicit val configMappable: Mappable[Config] =
    (m: Map[String, Map[String, Any]]) =>
      (to[Common].from(m("common")), to[Ftp].from(m("ftp"))).mapN(Config.apply)
}

final case class Common(basic_size_limit: Long, student_size_limit: Long, paid_users_size_limit: Long, path: String)

final case class Ftp(name: String, path: String, enabled: Boolean)