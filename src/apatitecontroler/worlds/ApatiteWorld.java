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
public abstract class ApatiteWorld {
    public abstract boolean isInvicibleOnSpawn();
    public abstract int getSpawnRadius();
    public abstract boolean canInteract();
    public abstract GameMode getDefaultGamemode();
    //public abstract boolean isInvicibleOnSpawn();
}
