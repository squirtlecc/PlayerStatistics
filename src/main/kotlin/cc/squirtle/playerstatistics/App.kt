package cc.squirtle.playerstatistics

import cc.squirtle.playerstatistics.core.commands.MainCmd
import cc.squirtle.playerstatistics.core.commands.ManageCmds
import cc.squirtle.playerstatistics.core.listeners.TaskListener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Bukkit
import cc.squirtle.playerstatistics.entity.CmdResult
import cc.squirtle.playerstatistics.entity.PluginEntity

import java.lang.Exception


class App: JavaPlugin() {
    override fun onEnable() {
        InitInstance()

        if (PluginRegisterCmds() && PluginRegisterEvents()) {
            PluginLoadMsgs()
        }
        // Register our command
    }

    override fun onDisable() {
        SendMsgOnDisable()
    }


    private fun InitInstance(){

        PluginEntity.INSTANCE = this
        PluginEntity.PLUGIN_DESCFILE = this.description
        PluginEntity.FILE_CONFIG = this.config

        // load the default configuration file to your plugin's folder
        //https://sodocumentation.net/bukkit/topic/6824/configuration-files

        this.saveDefaultConfig()
        this.reloadConfig()
    }

    /**
     * register all command in this function
     */
    private fun PluginRegisterCmds(): Boolean {
        try {
            ManageCmds.RegisterCmds("playerstatistics")
            CmdResult.SUCCESS(this.getCommand("playerstatistics").toString()).Send2Console()
        } catch (e: Throwable) {
            CmdResult.FAILED("GamePrompt Plugins cant Register Commands!").Send2Console()
            return false
        }
        return true
    }

    /**
     * register all events and listener in this function
     */
    private fun PluginRegisterEvents(): Boolean{

        try {
            TaskListener(this).RegisterListener()
            //this.server.pluginManager.registerEvents(PlayerListener(this),this)
        }catch (e: Throwable){
            println(e)
            CmdResult.FAILED("PlayerStatistics Plugin cant Register Events").Send2Console()
            return false
        }
        return true
    }

    /**
     * when plugins successful loading, console to remind messages
     */
    private fun PluginLoadMsgs(){
        CmdResult.NAME("Plugin Enable now").Send2Console()
        CmdResult.NAME("Minecraft Version: ${Bukkit.getBukkitVersion()}").Send2Console()

    }

    /**
     * when plugins disable ,to print messages
     */
    private fun SendMsgOnDisable(){
        CmdResult.FAILED("PlayerStatistics Plugin Disable now").Send2Console()
    }
}