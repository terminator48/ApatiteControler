/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler.worlds;

import org.bukkit.GameMode;

/**
 *
 * @author Daniil
 */
public class minigames extends ApatiteWorld{

    @Override
    public boolean isInvicibleOnSpawn() {
        return true;
    }

    @Override
    public int getSpawnRadius() {
        return 16;
    }

    @Override
    public boolean canInteract() {
        return false;
    }
    
    @Override
    public GameMode getDefaultGamemode() {
        return GameMode.SURVIVAL;
    }
    
}
