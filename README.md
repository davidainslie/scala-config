# Scala Configuration

Every large software project has its share of configuration files to control settings, execution, etc. Let's contemplate a config file format that looks a lot like standard PHP .ini files, but with a few tweaks.
A config file will appear as follows:

```properties
[common]
basic_size_limit = 26214400
student_size_limit = 52428800
paid_users_size_limit = 2147483648
path = /srv/var/tmp/
path<itscript> = /srv/tmp/

[ftp]
name = "hello there, ftp uploading"
path = /tmp/
path<production> = /srv/var/tmp/
path<staging> = /srv/uploads/
path<ubuntu> = /etc/var/uploads
enabled = no
; This is a comment

[http]
name = "http uploading"
path = /tmp/
path<production> = /srv/var/tmp/
path<staging> = /srv/uploads/ ; This is another comment
params = array,of,values
```

Where **[group]** denotes the start of a group of related config options, **setting = value** denotes a standard setting name and associated default value, and **setting<override> = value2** denotes the value for the setting if the given override is enabled.
If multiple enabled overrides are defined on a setting, the one defined last will have priority.

## Implementation

To parse an **ini** configuration into an ADT, implement [Mappable](src/main/scala/com/backwards/config/ini/Mappable.scala) for your top-level ADT configuration e.g.

```scala
import cats.implicits.catsSyntaxTuple2Semigroupal
import com.backwards.config.ini.Mappable
import com.backwards.config.ini.Mapping.to

final case class Config(common: Common, ftp: Ftp)

final case class Common(basic_size_limit: Long, student_size_limit: Long, paid_users_size_limit: Long, path: String)

final case class Ftp(name: String, path: String, enabled: Boolean)

// Mappable for the top-level Config (for convenience added to companion object)
object Config {
  implicit val configMappable: Mappable[Config] =
    (m: Map[String, Map[String, Any]]) =>
      (to[Common].from(m("common")), to[Ftp].from(m("ftp"))).mapN(Config.apply)
}
```

The above ADT can be generated from an **ini** (example above) with the following line:

```scala
import com.backwards.config.Config
import com.backwards.config.ini.Config.loadConfig

loadConfig[Config]("src/test/resources/test.ini")
```

Also, see the [Demo App](src/main/scala/com/backwards/config/DemoApp.scala)