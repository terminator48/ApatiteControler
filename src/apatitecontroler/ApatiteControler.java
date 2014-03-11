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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Daniil
 */
public class ApatiteControler  extends JavaPlugin implements Listener, CommandExecutor{
   public static final Logger _log = Logger.getLogger("Minecraft");
   public static RestorationManager rm;

   @Override
   public void onEnable() {
       _log.info("[ApatiteControler] I am enabled!"); //вывод произвольного текста в консоль сервера
       Bukkit.getPluginManager().registerEvents(this, this);
       Bukkit.getPluginManager().registerEvents(new HandleSignEvent(), this);
       Bukkit.getPluginManager().registerEvents(new MainWorldPlayerControler(), this);
       Bukkit.getPluginManager().registerEvents(new InventorySaver(), this);
       getCommand("changehub").setExecutor(new HubCommand());
       getCommand("setworldspawn").setExecutor(new WorldSpawnSetCommand());
       getCommand("hublist").setExecutor(this);
       getCommand("spawn").setExecutor(new SpawnCommand());
       rm = new RestorationManager(this.getDataFolder());
   }
   @Override
   public void onDisable() {
       Bukkit.broadcastMessage(ChatColor.GOLD+""+ChatColor.BOLD+"[ApatiteControler] Перезагрузка сервера через 3 секунды");
       try {
           Thread.sleep(1000);
       } catch (InterruptedException ex) {
           Logger.getLogger(ApatiteControler.class.getName()).log(Level.SEVERE, null, ex);
       }
       Bukkit.broadcastMessage(ChatColor.GOLD+""+ChatColor.BOLD+"[ApatiteControler] ..2");
       try {
           Thread.sleep(1000);
       } catch (InterruptedException ex) {
           Logger.getLogger(ApatiteControler.class.getName()).log(Level.SEVERE, null, ex);
       }
       Bukkit.broadcastMessage(ChatColor.GOLD+""+ChatColor.BOLD+"[ApatiteControler] ..1");
       try {
           Thread.sleep(1000);
       } catch (InterruptedException ex) {
           Logger.getLogger(ApatiteControler.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   @Override
   public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
       int i;
       for(i=0; i < Bukkit.getWorlds().size(); i++){
       arg0.sendMessage(Bukkit.getWorlds().get(i).getName());
       }
       return true;   
   }
}
