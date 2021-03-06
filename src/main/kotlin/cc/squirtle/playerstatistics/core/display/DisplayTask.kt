package cc.squirtle.playerstatistics.core.display

import cc.squirtle.StatsSideBar
import cc.squirtle.playerstatistics.App
import cc.squirtle.playerstatistics.entity.CmdResult
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class DisplayTask(player: Player) : BukkitRunnable()  {
    companion object{
        var display_task_id : MutableMap<UUID, Int> = hashMapOf()
        fun CancelTask(player: Player) {
            display_task_id[player.uniqueId]?.let { Bukkit.getScheduler().cancelTask(it) }
        }
    }

    private val player = player
    var loop = 0

    override fun run() {
        display_task_id[player.uniqueId] = taskId
        StatsSideBar.DealBoardData(player, StatsSideBar.total_borad_type[loop])
        if (loop < StatsSideBar.total_borad_type.size - 1) {
            loop++
        } else {
            loop = 0
        }
    }


    override fun cancel() {
        super.cancel()
    }

}