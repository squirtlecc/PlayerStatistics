package cc.squirtle;

import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;

public class StatsSideBar {

    //scoreboard cant have same score on board
    public static Scoreboard ShowStatBoard(Player dPlayer, String boardName, Map<UUID, Integer> totalScore){
        ScoreboardManager bmanage = Bukkit.getScoreboardManager();
        Scoreboard board = bmanage.getNewScoreboard();
        
        Objective obj = board.registerNewObjective("PlayerStats", "dummy",ChatColor.translateAlternateColorCodes('&', "&a&l"+boardName));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        int Rank_number = 10;
        for(Entry<UUID, Integer> entry : totalScore.entrySet()){
            //CmdResult.PlayerPrint(dPlayer, CmdResult.INFO(entry.getKey()+" : "+entry.getValue()));
            if(Rank_number<0) break; 
            OfflinePlayer ply = Bukkit.getOfflinePlayer(entry.getKey());
            //Bukkit.getPlayer(); <- only get online player
            Score score = obj.getScore(ply.getName());
            //score default value = 0, so this equal to exculde player of them never do this things
            if(score.getScore() < entry.getValue()){
                score.setScore(entry.getValue());
                Rank_number--;
            }
            
        }
        dPlayer.setScoreboard(board);
        return board;
    }
    
    public static void HideStatBoard(Player dPlayer){
        //for(Player online: Bukkit.getOnlinePlayers()){ }
        dPlayer.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
