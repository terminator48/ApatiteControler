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
public class EmptyWorld extends ApatiteWorld{
    public boolean isInvicibleOnSpawn = false;
    public int spawnRadius = 16;
    public boolean canInteract = true;
    public String gamemode = "survival";
    
    public EmptyWorld(boolean isInvOnSpawn, int spawnRad, boolean canInteract2, String gm){
        isInvicibleOnSpawn = isInvOnSpawn;
        spawnRadius= spawnRad;
        canInteract = canInteract2;
        gamemode = gm;
    }
    
    @Override
    public boolean isInvicibleOnSpawn() {
        return isInvicibleOnSpawn;
    }

    @Override
    public int getSpawnRadius() {
        return spawnRadius;
    }

    @Override
    public boolean canInteract() {
        return canInteract;
    }

    @Override
    public GameMode getDefaultGamemode() {
        GameMode gm = null;
        switch(gamemode){
          case "survival": 
              gm = GameMode.SURVIVAL; 
              break;
          case "creative": 
              gm = GameMode.CREATIVE; 
              break;
          default: gm = GameMode.ADVENTURE;
        }
        return gm;
    }
    
}
