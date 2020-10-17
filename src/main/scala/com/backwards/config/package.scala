package com.backwards

import com.backwards.mapping.Mappable

package object config {

  // TODO - REMOVE
  val ftpMap = Map(
    "name" -> "my-name",
    "path" -> "my-path",
    "enabled" -> true,
    "temp" -> 1
  )

  val configMap = Map(
    "ftp" -> ftpMap
  )
  // TODO

  def loadConfig[C: Mappable](path: String, overrides: String*): C =
    Mappable[C].map(configMap)
}