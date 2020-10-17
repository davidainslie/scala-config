package com.backwards.config.ini

import scala.annotation.tailrec
import scala.util.matching.Regex
import cats.Monad
import cats.effect.IO
import cats.implicits._
import better.files._

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
 *  - Boolean is: true | false | yes | no | 1 | 0 - As a configuration of say 1 could actually be to configure an Integer, we leave the interpretation of this when setting a Boolean type.
 */
object IniConfig {
  type IniMap = Map[String, Map[String, Any]]

  // TODO - Maybe update to use parser combinators instead.
  val ConfigRegex: Regex = """\[([\w]+)]""".r

  val PropRegex: Regex = """(\w+)\s*=\s*(.+)""".r

  val CommentRegex: Regex = """\s*?(;.*)""".r

  val PropValue: Regex = """^(.*?)(;.*)*$""".r

  val trim: String => String =
    _.trim

  val simplify: String => String = trim andThen {
    case PropValue(s, _) => trim.andThen { s =>
      if (s.startsWith("\"") && s.endsWith("\"")) s.init.drop(1)
      else if (s.startsWith("\'") && s.endsWith("\'")) s.init.drop(1)
      else s
    }(s)
  }

  def parse(props: List[String]): IniMap = {
    @tailrec
    def parseProps(props: List[String], config: Map[String, Any]): (List[String], Map[String, Any]) = props match {
      case PropRegex(k, v) :: rest =>
        parseProps(rest, config + (k.trim -> simplify(v)))

      case CommentRegex(_) :: rest =>
        parseProps(rest, config)

      case rest =>
        rest -> config
    }

    @tailrec
    def parseConfig(props: List[String], config: IniMap): IniMap = props match {
      case ConfigRegex(prop) :: rest =>
        parseProps(rest, Map.empty[String, Any]) match {
          case (rest, c) => parseConfig(rest, config ++ Map(prop.trim -> c))
        }

      case _ =>
        config
    }

    parseConfig(props, Map.empty[String, Map[String, Any]])
  }

 /* def parse[F[_]: Monad](path: String): F[IniMap] = {
    val xs: Iterable[String] = File(path).lines

    parse(File(path).lines)
  }*/
}