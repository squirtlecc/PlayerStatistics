package cc.squirtle

import cc.squirtle.StatsSideBar.ShowStatBoard
import cc.squirtle.playerstatistics.entity.CmdResult
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.scoreboard.*
import java.util.*
import kotlin.collections.ArrayList
import org.bukkit.Statistic
import java.util.UUID

import java.util.HashMap
import org.bukkit.Material
import kotlin.math.roundToInt


object StatsSideBar {
    val total_borad_type : ArrayList<String> = arrayListOf("playtime","mined","death","walk","fish")

    //scoreboard cant have same score on board
    fun ShowStatBoard(dPlayer: Player, boardName: String, totalScore: Map<UUID, Int>): Scoreboard {
        val borad_manage = Bukkit.getScoreboardManager()
        val board = borad_manage!!.newScoreboard
        val obj = board.registerNewObjective("PlayerStats", "dummy",
            ChatColor.translateAlternateColorCodes('&', "&a&l$boardName"))

        obj.displaySlot = DisplaySlot.SIDEBAR

        var Rank_number = 10
        for ((uuid, value) in totalScore) {
            //CmdResult.PlayerPrint(dPlayer, CmdResult.INFO(entry.getKey()+" : "+entry.getValue()));
            if (Rank_number < 0) break
            val ply = Bukkit.getOfflinePlayer(uuid)
            //Bukkit.getPlayer(); <- only get online player
            val score = obj.getScore(ply.name!!)
            //score default value = 0, so this equal to exculde player of them never do this things
            if (score.score < value) {
                score.score = value
                Rank_number--
            }
        }
        dPlayer.scoreboard = board
        return board
    }

    /**
     * function of hide side bar
     */
    fun HideStatBoard(dPlayer: Player) {
        //for(Player online: Bukkit.getOnlinePlayers()){ }
        dPlayer.scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard
    }

    fun DealBoardData(dPlayer: Player?, boardType: String) {
        var map_sort: Map<UUID,Int> = mapOf()
        var board_name = "排行榜"

        when (boardType.toLowerCase()) {
            "death" -> {
                board_name = "死亡榜"
                map_sort = PlayerDataCommon(Statistic.DEATHS, 1.0f)
            }
            "mined" ->{
              board_name = "挖石榜"
              map_sort = PlayerMinedData()
            }
            "playtime" ->{
                board_name = "在线榜/小时"
                map_sort = PlayerDataCommon(Statistic.PLAY_ONE_MINUTE, 1.0f / 20 / 3600)
            }
            "walk" -> {
                board_name = "探险榜/米"
                map_sort = PlayerDataCommon(Statistic.WALK_ONE_CM,1.0f/100 )
            }
           "fish" -> {
                board_name = "钓鱼榜"
                map_sort = PlayerDataCommon(Statistic.FISH_CAUGHT, 1.0f / 100)
        }
            else -> {
                board_name = "在线榜/小时"
                map_sort = PlayerDataCommon(Statistic.PLAY_ONE_MINUTE, 1.0f / 20 / 3600)
            }
        }
        // to sort all map  (on support positive number)
        map_sort = map_sort.entries.sortedBy { -it.value }.associateBy({ it.key }, { it.value })

        ShowStatBoard(dPlayer!!,board_name,map_sort)

    }

    /**
     * common function of get player statistics
     */
    fun PlayerDataCommon(common: Statistic?, multi: Float): Map<UUID, Int> {
        val result: MutableMap<UUID, Int> = HashMap()
        for (oPlayer in Bukkit.getOfflinePlayers()) {
            //String player_name = oPlayer.getName();
            val uuid = oPlayer.uniqueId
            val data = (oPlayer.getStatistic(common!!) * multi).roundToInt()
            result[uuid] = data
        }
        return result
    }

    fun PlayerMinedData(): Map<UUID, Int> {
        val result: MutableMap<UUID, Int> = HashMap()
        for (oPlayer in Bukkit.getOfflinePlayers()) {
            val uuid = oPlayer.uniqueId
            val data = oPlayer.getStatistic(Statistic.MINE_BLOCK, Material.STONE)
            //TODO: static all stone data of mined
            result[uuid] = data
        }
        return result
    }

}