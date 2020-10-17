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