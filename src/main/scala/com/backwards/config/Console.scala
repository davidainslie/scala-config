package com.backwards.config

import scala.Console._

object Console {
  val red: String => String =
    RED + _ + RESET

  val green: String => String =
    GREEN + _ + RESET
}