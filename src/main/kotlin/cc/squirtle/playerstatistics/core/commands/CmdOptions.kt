package cc.squirtle.playerstatistics.core.commands

import cc.squirtle.playerstatistics.entity.CmdResult
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.command.TabExecutor
import java.util.ArrayList




object CmdOptions: TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>,
    ): ArrayList<String> {
        var entityTypes = ArrayList<String>()
        when(args.size){
            1 -> {
                entityTypes = SubcmdOption()
            }
            2 -> {
                entityTypes = ThircmdOption(args[0])
            }
        }
        return entityTypes
    }

    fun SubcmdOption(): ArrayList<String> {
        val entityTypes = ArrayList<String>()
        entityTypes.add("hide")
        entityTypes.add("show")
        return entityTypes
    }

    fun ThircmdOption(cmd :String): ArrayList<String> {
        val entityTypes = ArrayList<String>()
        when(cmd) {
            "show" -> {
                entityTypes.add("mined")
                entityTypes.add("walk")
                entityTypes.add("playtime")
                entityTypes.add("mined")
                entityTypes.add("fish")
                entityTypes.add("death")
            }
//            else ->{
//                for(ply in Bukkit.getOnlinePlayers()){
//                    entityTypes.add(ply.name)
//                }
//            }
        }
        return entityTypes
    }

}