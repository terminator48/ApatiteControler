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
public class Default extends ApatiteWorld{

    @Override
    public boolean isInvicibleOnSpawn() {
        return false;
    }

    @Override
    public int getSpawnRadius() {
        return 16;
    }

    @Override
    public boolean canInteract() {
        return true;
    }

    @Override
    public GameMode getDefaultGamemode() {
        return GameMode.SURVIVAL;
    }
    
    
}
