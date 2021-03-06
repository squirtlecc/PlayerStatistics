package cc.squirtle.playerstatistics.core.commands

import cc.squirtle.playerstatistics.entity.PluginEntity

object ManageCmds {
    fun RegisterCmds(cmd: String){

        when(cmd) {
            "playerstatistics" -> {
                PluginEntity.INSTANCE!!.getCommand(cmd)!!.setExecutor(MainCmd)
                PluginEntity.INSTANCE!!.getCommand(cmd)!!.tabCompleter = CmdOptions
            }
        }

    }

}