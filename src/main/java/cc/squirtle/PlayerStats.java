package cc.squirtle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import cc.squirtle.Entity.*;

import cc.squirtle.util.MapSort;

public class PlayerStats implements CommandExecutor {
    private int task = -1;
    
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            // console command
            return true;
        }
        Player player = (Player) sender;
        if(args.length == 0){
            // when command no args
            player.sendMessage(ChatColor.YELLOW+"输入/sps show 显示计分板");
            return true;
        }
        //player command
        
        List<String> total_borad_type = new LinkedList<String>(){/**
			 *
			 */
			private static final long serialVersionUID = 1L;
		{
            add("OnlineTime");
            add("Mined");
            add("Death");
            add("Walk");
            add("Fish");

        }};
        if(args[0].equalsIgnoreCase("show")){
            //player.getUniqueId().toString()
            if(args.length == 1){
                player.sendMessage(ChatColor.GREEN+"PlayerStatic 已显示/10s 刷新");
                //set board
                
                task = Bukkit.getScheduler().scheduleSyncRepeatingTask(App.getPlugin(App.class), new Runnable(){
                    int loop = 0;
                    @Override
                    public void run() {
                        DealBoardData(player,total_borad_type.get(loop));
                        if(loop < total_borad_type.size()-1){
                            loop++;
                        }else{
                            loop = 0;
                        }
                    }
                }, 0, 200); //200ticks = 10s
    
                //setting task id when player quit game
            }else{
                DealBoardData(player,args[1]);
            }


            
            //player.setScoreboard(ssb.ShowStatBoard("Top death", DealBoardData("Death")));
        }else if(args[0].equalsIgnoreCase("hide")){
            // kill_entity <- need type
            if(task != -1){
                Bukkit.getScheduler().cancelTask(task);
                task = -1;
            }
                StatsSideBar.HideStatBoard(player);
                CmdResult.PlayerPrint(player, CmdResult.NOTICE("计分板 已关闭"));
  

        }
        if(args[0].equalsIgnoreCase("player")){
            // kill_entity <- need type
            for(OfflinePlayer ply: Bukkit.getOfflinePlayers()){
                CmdResult.PlayerPrint(player, CmdResult.INFO(ply.getName()+" : "+ply.getUniqueId()));
            }
        }

        // If the player (or console) uses our command correct, we can return true
        return false;
    }


    public void DealBoardData(Player dPlayer, String boardType){
        MapSort<Integer> mapsort = new MapSort<>();
        //Map<String,Integer> result = new HashMap<>();
        switch(boardType.toLowerCase()) {
            case "death":
                StatsSideBar.ShowStatBoard(dPlayer, "死亡榜", 
                mapsort.sortMapByValue(PlayerDataCommon(Statistic.DEATHS,1.0f)));
            break;
            case "mined":
                StatsSideBar.ShowStatBoard(dPlayer, "挖石榜", 
                mapsort.sortMapByValue(PlayerMinedData()));
            break;
            case "onlineTime":
                StatsSideBar.ShowStatBoard(dPlayer, "在线榜/小时", 
                mapsort.sortMapByValue(PlayerDataCommon(Statistic.PLAY_ONE_MINUTE,1.0f/20/3600)));
            break;
            case "walk":
                StatsSideBar.ShowStatBoard(dPlayer, "探险榜/米", 
                mapsort.sortMapByValue(PlayerDataCommon(Statistic.WALK_ONE_CM,1.0f/100 )));
            break;
            case "fish":
                StatsSideBar.ShowStatBoard(dPlayer, "钓鱼榜", 
                mapsort.sortMapByValue(PlayerDataCommon(Statistic.FISH_CAUGHT,1.0f)));
            break;
            default:
                StatsSideBar.ShowStatBoard(dPlayer, "在线榜/小时", 
                mapsort.sortMapByValue(PlayerDataCommon(Statistic.PLAY_ONE_MINUTE,1.0f/20/3600)));
        }
    }
    // get player death data (through uuid.json using bukkit api)


    public Map<UUID, Integer> PlayerMinedData(){
        Map<UUID,Integer> result = new HashMap<>();
        for(OfflinePlayer oPlayer : Bukkit.getOfflinePlayers()){
            UUID uuid = oPlayer.getUniqueId();
            int data = oPlayer.getStatistic(Statistic.MINE_BLOCK, Material.STONE);
            //TODO: static all stone data of mined
            result.put(uuid,data);
        }
        return result;
    }
    public Map<String, Integer> PlayerBuildData(){
        Map<String,Integer> result = new HashMap<>();
        //TODO:
        return result;
    }

    public Map<UUID, Integer> PlayerOnlineTimeData(){
        Map<UUID,Integer> result = new HashMap<>();
        for(OfflinePlayer oPlayer : Bukkit.getOfflinePlayers()){
            result.put(oPlayer.getUniqueId(),Math.round( oPlayer.getStatistic(Statistic.PLAY_ONE_MINUTE)/20.0f/3600));
        }
        return result;
    }

    public Map<UUID, Integer> PlayerDataCommon(Statistic common, float multi){
        Map<UUID,Integer> result = new HashMap<>();
        for(OfflinePlayer oPlayer : Bukkit.getOfflinePlayers()){
            //String player_name = oPlayer.getName();
            UUID uuid = oPlayer.getUniqueId();
            int data = Math.round( oPlayer.getStatistic(common)*multi);
            result.put(uuid,data);
        }
        return result;
    }


	public int getTask() {
		return task;
	}


	public void setTask(int task) {
		this.task = task;
	}
}