@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

package cc.squirtle.playerstatistics.core.commands



import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


import org.bukkit.entity.Player

import org.bukkit.Bukkit


import cc.squirtle.StatsSideBar

import cc.squirtle.playerstatistics.core.display.DisplayTask
import cc.squirtle.playerstatistics.entity.CmdResult
import cc.squirtle.playerstatistics.entity.PluginEntity


object MainCmd: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            // console command
            return true
        }
        val ply: Player = sender
        if (args.size === 0) {
            // when command no args
            CmdResult.FAILED("输入/sps show 显示计分板").Send2Player(ply)
            return true
        }
        if (args[0].equals("show", ignoreCase = true)) {
            //player.getUniqueId().toString()
            if (args.size == 1) {
                CmdResult.RAINBOW("PlayerStatic 已显示/10s 刷新").Send2Player(ply)
                //set board
                //200ticks = 10s
                DisplayTask(ply).runTaskTimer(PluginEntity.INSTANCE!!, 0, 200)

                //setting task id when player quit game
            } else {
                StatsSideBar.DealBoardData(ply,args[1])
            }
            return true

        } else if (args[0].equals("hide", ignoreCase = true)) {
            // kill_entity <- need type
            DisplayTask.CancelTask(ply)
            StatsSideBar.HideStatBoard(ply)
            CmdResult.RAINBOW("PlayerStatic 计分板 已关闭").Send2Player(ply)
            return true
        }

        return false
    }
}