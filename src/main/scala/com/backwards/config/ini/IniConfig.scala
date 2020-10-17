package com.backwards.config.ini

import cats.Monad
import cats.effect.IO
import cats.implicits._

/**
 * INI file is of the form:
 * {{{
 *  [ftp]
 *  name = "hello there, ftp uploading"
 *  enabled = true
 *
 *  [http]
 *  params = list, of, values
 * }}}
 *
 * where its Map representation is of the form:
 * {{{
 *   Map(
 *    "ftp" -> Map(
 *      "name" -> "hello there, ftp uploading",
 *      "enabled" -> true
 *    ),
 *    "http" -> Map(
 *      "params" -> List("list", "of", "values")
 *    )
 *   )
 * }}}
 *
 * Caveats:
 *
 *  - [group] must be a valid name for a Scala variable e.g. [ftp] within a Config class can be accessed as config.ftp
 *  - Property identifier must be a valid name for a Scala variable e.g. enabled = true within group [ftp] can be accessed from a Config class as config.ftp.enabled
 *  - Boolean is: true | false | yes | no | 1 | 0 - However the actual interpretation is left as a type class TODO
 */
object IniConfig {
  type IniMap = Map[String, Map[String, Any]]

  /*def parse[F[_]](path: String): F[IniMap] = {

  }*/

  // TODO - Maybe use parser combinators instead.

  val ConfigRegex = """\[([\w]+)]""".r

  val PropRegex = """(\w+)\s*=\s*(.+)""".r

  //val BooleanRegex =

  def parse(props: List[String]): IniMap = {
    println(s"let's parse $props")

    def parseProps(props: List[String], config: Map[String, Any]): (List[String], Map[String, Any]) = props match {
      case PropRegex(k, v) :: rest =>
        println(s"===> Matched prop $k, $v")

        parseProps(rest, config + (k.trim -> parseValue(v.trim)))
      case rest =>
        println(s"===> End of prop $rest")
        rest -> config
    }

    def parseConfig(props: List[String], config: IniMap): IniMap = props match {
      case ConfigRegex(prop) :: rest =>
        println(s"===> Matched config prop $prop")

        parseProps(rest, Map.empty[String, Any]) match {
          case (rest, c) => parseConfig(rest, config ++ Map(prop.trim -> c))
        }


      case blah =>
        println(s"Got $blah")
        config
    }

    parseConfig(props, Map.empty[String, Map[String, Any]])
  }

  def parseValue(v: String): Any = {
    v
  }
}