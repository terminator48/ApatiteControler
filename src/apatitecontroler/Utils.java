/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import apatitecontroler.worlds.ApatiteWorld;
import org.bukkit.Location;
import org.bukkit.World;

/**
 *
 * @author Daniil
 */
public class Utils {

    public static void sendMsg(Player p, String str) {
        p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[ApatiteControler] " + str);
    }

    public static String getDefaultWorld() {
        return Bukkit.getWorlds().get(0).getName();
    }

    public static ApatiteWorld getWorld(String worldName) {
        return (ApatiteWorld) ApatiteControler.worlds.get(worldName);
    }

    public static boolean worldExists(String worldName) {
        return getWorld(worldName) != null;
    }

    public static boolean teleportPlayerToWorld(Player pl, String worldName) {

        if (!worldExists(worldName)) {
            Utils.sendMsg(pl, ChatColor.RED + "Мир '" + worldName + "' не найден!");
            return false;
        }

        World wl = Bukkit.getWorld(worldName);

        if (!Utils.hasPermission(pl, "ac.teleport." + wl.getName())) {
            Utils.sendMsg(pl, ChatColor.RED + "У вас недостаточно прав телепортироваться в мир " + wl.getName() + "!");
            return false;
        }
        
        pl.teleport(new Location(wl, wl.getSpawnLocation().getX(), wl.getSpawnLocation().getY(), wl.getSpawnLocation().getZ()));
        Utils.sendMsg(pl, ChatColor.GREEN + "Телепортирую!");
        return true;
    }

    public static boolean hasPermission(Player pl, String permission) {
        if (pl.hasPermission(permission) || pl.isOp()) {
            return true;
        } else {
            return false;
        }
    }
}
