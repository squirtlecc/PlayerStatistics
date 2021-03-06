package cc.squirtle.playerstatistics.core.listeners
import cc.squirtle.playerstatistics.App


import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.PluginManager


/**
 * Listener player event when they playing game
 * PlayerListener
 */
class TaskListener(instance: App) : Listener {
    private val instance: App = instance


    @EventHandler(priority = EventPriority.LOWEST)
    fun PlayerQuit(event: PlayerQuitEvent?) {
        Bukkit.getScheduler().cancelTasks(instance!!)
        return
    }

    fun RegisterListener(): PluginManager {
        val pm = instance.server.pluginManager
        pm.registerEvents(this, instance)
        return pm
    }


}