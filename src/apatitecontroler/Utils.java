/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import apatitecontroler.worlds.ApatiteWorld;
import apatitecontroler.worlds.CreativeWorld;
import apatitecontroler.worlds.Default;
import apatitecontroler.worlds.MainLobby;
import apatitecontroler.worlds.minigames;

/**
 *
 * @author Daniil
 */
public class Utils {
    
    public static void sendMsg(Player p, String str){
       p.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"[ApatiteControler] "+str);
   }
   public static String getDefaultWorld(){
       //ApatiteControler.rm
       return Bukkit.getWorlds().get(0).getName();
   }
   public static ApatiteWorld getWorld(String worldName){
        if(worldName == getDefaultWorld()){
            return new MainLobby();
        }else if(worldName.equals("minigames")){
            return new minigames();
        }else  if(worldName.equals("creativ")){
            return new CreativeWorld();
        }else return new Default();
   }
   public static boolean hasPermission(Player pl, String permission){
       if(pl.hasPermission(permission) || pl.isOp()) return true;
       return false;
   }
}
