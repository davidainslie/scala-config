package com.backwards.config

import cats.implicits._
import com.backwards.config.ini.Mappable
import com.backwards.config.ini.Mapping.to

final case class Config(common: Common, ftp: Ftp, http: Http)

object Config {
  implicit val configMappable: Mappable[Config] =
    (m: Map[String, Map[String, Any]]) =>
      (to[Common].from(m("common")), to[Ftp].from(m("ftp")), to[Http].from(m("http"))).mapN(Config.apply)
}

final case class Common(basic_size_limit: Long, student_size_limit: Long, paid_users_size_limit: Long, path: String)

final case class Ftp(name: String, path: String, enabled: Boolean)

final case class Http(name: String, path: String)