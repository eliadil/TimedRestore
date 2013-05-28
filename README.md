TimedRestore
====================================

TimedRestore is a plugin for the Minecraft wrapper [Bukkit](http://bukkit.org/) that allows adminstrators to configure the automatic restoration of [WorldGuard](dev.bukkit.org/server-mods/worldguard) regions to a previous [WorldEdit](dev.bukkit.org/server-mods/worldedit/‎
) snapshot. This is useful to reset guest or other public areas back to a known good state.

## Features

- Simple and easy to configure.
- Utilises cron4j for flexible scheduling of restore tasks.
- Restore a number of regions in one task.
- Use any snapshot that WorldEdit supports.
- Automatically teleports players out of regions which are being restored.

## License

TimedRestore is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

TimedRestore is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

## Documentation

Many of the features specific to TimedRestore are documented [on the website](http://grandwazir.github.com/TimedRestore). If you are looking to change the messages used in TimedRestore or localise the plugin into your own language you will want to look at [this page](https://github.com/grandwazir/BukkitUtilities/wiki/Localisation) instead.

## Installation

Before installing, you need to make sure you are running at least the latest [recommended build](http://dl.bukkit.org/latest-rb/craftbukkit.jar) for Bukkit. Support is only given for problems when using a recommended build. This does not mean that the plugin will not work on other versions of Bukkit, the likelihood is it will, but it is not supported.

### Getting the latest version

The best way to install TimedRestore is to get it direct from my [website](http://grandwazir.github.com/TimedRestore/download.html). 

Alternatively you can download the latest version from [BukkitDev](http://dev.bukkit.org/server-mods/timedrestore/).

### Getting older versions

[Older versions](http://grandwazir.github.com/TimedRestore/download.html) are available as well, however they are not supported. If you are forced to use an older version for whatever reason, please let me know why by [opening a issue](https://github.com/grandwazir/TimedRestore/issues/new) on GitHub.

### Building from source

You can also build TimedRestore from the source if you would prefer to do so. This is useful for those who wish to modify TimedRestore before using it. Note it is no longer necessary to do this to alter messages in the plugin. Instead you should read the documentation on how to localise the plugin instead. This assumes that you have Maven and git installed on your computer.

    git clone git://github.com/grandwazir/TimedRestore.git
    cd TimedRestore
    mvn install

## Reporting issues

If you are a server administrator and you are requesting support in installing or using the plugin you should [make a post](http://dev.bukkit.org/server-mods/timedrestore/forum/create-thread/) in the forum on BukkitDev. If you want to make a bug report or feature request please do so using the [issue tracking](https://github.com/grandwazir/TimedRestore/issues) on GitHub.
