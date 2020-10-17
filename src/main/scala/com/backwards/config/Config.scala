package com.backwards.config

import com.backwards.mapping._

final case class Config(common: Common, ftp: Ftp)

object Config {
  implicit val configMappable: Mappable[Config] =
    (m: Map[String, Map[String, Any]]) =>
      Config(
        to[Common].from(m("common")).getOrElse(sys.error("Whoops")),
        to[Ftp].from(m("ftp")).getOrElse(sys.error("Whoops"))
      )
}

final case class Common(basic_size_limit: Long, student_size_limit: Long, paid_users_size_limit: Long, path: String)

final case class Ftp(name: String, path: String, enabled: Boolean)