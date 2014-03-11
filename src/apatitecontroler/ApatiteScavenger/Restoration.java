/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler.ApatiteScavenger;

import java.io.Serializable;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author Daniil
 */
public class Restoration implements Serializable{
  public boolean enabled;
  public ItemStack[] inventory;
  public ItemStack[] armour;
  public int level;
  public float exp;
}
