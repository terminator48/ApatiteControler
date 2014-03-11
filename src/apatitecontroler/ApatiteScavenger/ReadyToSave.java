/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler.ApatiteScavenger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Daniil
 */
public class ReadyToSave implements Serializable{
  
  public boolean enabled;
  public List<String> inventory = new ArrayList();
  public List<String> armour = new ArrayList();
  public int level;
  public float exp;

}
