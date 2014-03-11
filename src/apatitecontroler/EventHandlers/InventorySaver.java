/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler.EventHandlers;

import apatitecontroler.ApatiteControler;
import apatitecontroler.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffectType;
import apatitecontroler.worlds.ApatiteWorld;

/**
 *
 * @author Daniil
 */
public class InventorySaver implements Listener{
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e){
        if(e.getFrom().getWorld().equals(e.getTo().getWorld())) return;
        ApatiteControler.rm.saveInventory(e.getPlayer(), e.getFrom().getWorld().getName());
        ApatiteControler.rm.restoreInventory(e.getPlayer(), e.getTo().getWorld().getName());
    } 
}
