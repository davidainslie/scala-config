package com.backwards.config

import cats.implicits._

/**
 * Configuration of Booleans can be: true | false | yes | no | 1 | 0
 */
object BooleanConfig {
  val truthy = List("true", "yes", "on", "1")
  val falsey = List("false", "no", "off", "0")

  def boolean(v: Any): Option[Boolean] =
    if (truthy.contains(v.toString.toLowerCase))
      true.some
    else if (falsey.contains(v.toString.toLowerCase))
      false.some
    else
      none
}