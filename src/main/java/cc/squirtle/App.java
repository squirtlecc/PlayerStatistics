package cc.squirtle;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import cc.squirtle.Entity.CmdResult;
import cc.squirtle.Listeners.PlayerListener;
import net.md_5.bungee.api.ChatColor;


public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        
        
        PluginRisterEvents();
        this.getCommand("sps").setExecutor(new PlayerStats());

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+
        "[PlayerStatistics]-----------"+"PlayerStatistics"+"-----------"); 
        getLogger().info("  ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+
        "[PlayerStatistics] PlayerStatistics Plugins onEnable is called!");
        getLogger().info("  ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+
        "[PlayerStatistics]-----------"+"----------------"+"-----------");
        // Register our command
        

    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerStatistics Plugins onDisable is called!");
    }


    private boolean PluginRisterEvents(){
        PlayerListener player_listener = new PlayerListener(this);
        //PluginManager pm = player_listener.RegisterListener();
        try {
            this.getServer().getPluginManager().registerEvents(player_listener, this);
        } catch (Exception e) {
            CmdResult.ConsolePrint(CmdResult.FAILED("GamePrompt Plugins cant startup!") );
            return false;
        }
        return true;
    }

}