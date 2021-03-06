package cc.squirtle.playerstatistics.entity

import cc.squirtle.playerstatistics.App
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.PluginDescriptionFile

/**
 * Universal variable and method storage
 */
object PluginEntity {

    var INSTANCE: App? = null
	var PLUGIN_DESCFILE: PluginDescriptionFile? = null
	var FILE_CONFIG: FileConfiguration? = null
    var MUIL_FILECONFIG: MutableMap<String, FileConfiguration>? = null
}