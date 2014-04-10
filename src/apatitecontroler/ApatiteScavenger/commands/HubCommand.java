/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler.ApatiteScavenger.commands;

import apatitecontroler.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Daniil
 */
public class HubCommand implements CommandExecutor{
       @Override
       public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
       
       if(!(arg0 instanceof Player)) return true;
       Player pl = (Player) arg0;
       String worldName;
       
       if(arg3.length == 0) worldName = Utils.getDefaultWorld(); else worldName = arg3[0];
       
       Utils.teleportPlayerToWorld(pl, worldName);
       
       return true;
   }
}
