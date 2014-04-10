/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler;

import apatitecontroler.ApatiteScavenger.commands.WorldSpawnSetCommand;
import apatitecontroler.ApatiteScavenger.commands.HubCommand;
import apatitecontroler.ApatiteScavenger.commands.SpawnCommand;
import apatitecontroler.ApatiteScavenger.RestorationManager;

import apatitecontroler.EventHandlers.HandleSignEvent;
import apatitecontroler.EventHandlers.InventorySaver;
import apatitecontroler.EventHandlers.MainWorldPlayerControler;
import apatitecontroler.worlds.EmptyWorld;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Daniil
 */
public class ApatiteControler extends JavaPlugin implements Listener, CommandExecutor {

    public static final Logger _log = Logger.getLogger("Minecraft");
    public static RestorationManager rm;
    public static FileConfiguration config = null;
    public static EmptyWorld[] rw = null;
    public static boolean AchtungEnabled = false;
    public static Map worlds = new HashMap< String, EmptyWorld>();
    
    
    public void loadConfig() {
        this.saveDefaultConfig();
        config = this.getConfig();
    }

    public void loadWorlds() {
        Set<String> configuratedWorlds = config.getConfigurationSection("worlds").getKeys(false);
        for (String worldName : configuratedWorlds) {
            if(Bukkit.getWorld(worldName) != null){
                
                worlds.put(worldName, new EmptyWorld(config.getBoolean("worlds."+worldName+".invisibleOnSpawn", true), config.getInt("worlds."+worldName+".spawnRaduis", 16), config.getBoolean("worlds."+worldName+".canInteract", true), config.getString("worlds."+worldName+".gamemode", "survival") ));
                
                System.out.print("[ApatiteControler] World "+worldName+" enabled!");
            }else System.out.print("[ApatiteControler] World "+worldName+" not found!");
        }
    }

    @Override
    public void onEnable() {
        _log.info("[ApatiteControler] enabled!"); //вывод произвольного текста в консоль сервера

        loadConfig();
        loadWorlds();

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new HandleSignEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MainWorldPlayerControler(), this);


        if (config.getBoolean("separate_inventories")) {
            Bukkit.getPluginManager().registerEvents(new InventorySaver(), this);
        }

        getCommand("changehub").setExecutor(new HubCommand());
        getCommand("setworldspawn").setExecutor(new WorldSpawnSetCommand());
        getCommand("hublist").setExecutor(this);
        getCommand("spawn").setExecutor(new SpawnCommand());

        rm = new RestorationManager(this.getDataFolder());
    }

    @Override
    public void onDisable() {
        if (config.getBoolean("enable_reload_countdown")) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[ApatiteControler] Перезагрузка сервера через 3 секунды");
            try {
                Thread.sleep(1000);
                Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[ApatiteControler] ..2");
                Thread.sleep(1000);
                Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[ApatiteControler] ..1");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }

        this.saveConfig();
    }

    
    //HUBLIST COMMAND
    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        int i;
        Player pl = (Player) arg0;
        
        pl.sendMessage(ChatColor.RED+"Список загруженных миров");
        pl.sendMessage(ChatColor.RED+"========================");
        
        Set iter = worlds.keySet();
        String wl = "";
        for (Object worldObject: iter) {
            wl = (String) worldObject;
            arg0.sendMessage(ChatColor.GOLD+wl);
        }
        return true;
    }
}
