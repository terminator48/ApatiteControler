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
public class WorldSpawnSetCommand implements CommandExecutor{
       @Override
       public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
            if(!(arg0 instanceof Player)) return true;
            Player pl = (Player) arg0;
            World wl = pl.getLocation().getWorld();
            if(!(Utils.hasPermission(pl,"ac.setspawn"))){Utils.sendMsg(pl,ChatColor.RED+"У вас недостаточно прав устанавливать спавн в мире "+wl.getName()+"!"); return true;}
            wl.setSpawnLocation(pl.getLocation().getBlockX(), pl.getLocation().getBlockY(), pl.getLocation().getBlockZ());
            Utils.sendMsg(pl,ChatColor.GREEN+"Спавн установлен!");
            return true;
   }
}
