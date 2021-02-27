package cc.squirtle.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

import cc.squirtle.App;


/**
 * Listener player event when they playing game
 * PlayerListener
 */
public class PlayerListener implements Listener{
    
    public static App instance;
    public PlayerListener(){}
    public PlayerListener(App instance){
        PlayerListener.instance = instance;
    }



    @EventHandler(priority = EventPriority.LOWEST)
    public void PlayerJoinON(PlayerJoinEvent event){
        //Player ply = event.getPlayer();
        return;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void PlayerQuit(PlayerQuitEvent event){
        //Bukkit.getScheduler().cancelTask(PlayerStats.getTask());
        Bukkit.getScheduler().cancelTasks(instance);
        return;
    }


    public PluginManager RegisterListener(){
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(instance), instance);
        return pm;
    }
    
}