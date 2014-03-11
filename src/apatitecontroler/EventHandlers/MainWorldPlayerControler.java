/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler.EventHandlers;

import apatitecontroler.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import apatitecontroler.worlds.ApatiteWorld;

/**
 *
 * @author Daniil
 */
public class MainWorldPlayerControler  implements Listener{
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e){
        ApatiteWorld aw= Utils.getWorld(e.getPlayer().getLocation().getWorld().getName());        
        if(!aw.canInteract()){
            e.setCancelled(true);
        }   
    }
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player) {
            Player pl = (Player) e.getDamager();
        ApatiteWorld aw= Utils.getWorld(pl.getLocation().getWorld().getName());        
        if(!aw.canInteract() && pl.getWorld().getSpawnLocation().distance(pl.getLocation()) <=aw.getSpawnRadius()){
            e.setCancelled(true);
        }   
        } else {
            return;
        }
    }
    /*public void removeEffect(Player pl, PotionEffectType pet, int aplifier){
        
    }*/
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        ApatiteWorld aw= Utils.getWorld(e.getPlayer().getLocation().getWorld().getName());
        if(e.getTo().getBlockY() < 0) e.getPlayer().teleport(Bukkit.getWorld(Utils.getDefaultWorld()).getSpawnLocation());
        
        if(aw.isInvicibleOnSpawn()){
            if(e.getTo().distance(e.getPlayer().getWorld().getSpawnLocation()) <= aw.getSpawnRadius()){
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000,10));
            }else {e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);}
        }else{}
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e){
        ApatiteWorld aw = Utils.getWorld(e.getTo().getWorld().getName());
        if(!aw.isInvicibleOnSpawn()) e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
        GameMode gm = aw.getDefaultGamemode();
        if(!gm.equals(e.getPlayer().getGameMode())){
            e.getPlayer().setGameMode(gm);
            Utils.sendMsg(e.getPlayer(), ChatColor.GREEN+"Игровой режим изменён в соответствии с требованиями этого мира");
        }
            
    }
}
