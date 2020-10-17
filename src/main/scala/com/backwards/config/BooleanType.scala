package com.backwards.config

import cats.implicits._

/**
 * Configuration of Booleans can be: true | false | yes | no | 1 | 0
 * @param value Any
 */
case class BooleanType(value: Any) extends AnyVal

object BooleanType {
  val trues = List("true", "yes", "on", "1")
  val falses = List("false", "no", "off", "0")

  def unapply(b: BooleanType): Option[Boolean] =
    if (trues.contains(b.value.toString.toLowerCase))
      true.some
    else if (falses.contains(b.value.toString.toLowerCase))
      false.some
    else
      none
}