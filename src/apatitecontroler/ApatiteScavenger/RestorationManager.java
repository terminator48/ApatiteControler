/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler.ApatiteScavenger;

import apatitecontroler.ApatiteControler;
import apatitecontroler.Utils;
import com.comphenix.protocol.utility.StreamSerializer;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Daniil
 */
public class RestorationManager {
    public File datafolder = null;
    private Object quarks;

    public RestorationManager(File dataFolder) {
       datafolder = dataFolder;
    }
    public Map convertStr2Map(String str){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(str, str);
        return map;
    }
    public ReadyToSave readyToSave(Restoration value)
    {
        StreamSerializer serializer;
        serializer = new StreamSerializer();
        int iC = 0;
        
        ReadyToSave restoration_s = new ReadyToSave();
        for (ItemStack i : value.inventory) {
            if ((i instanceof ItemStack))
            {
                try
                {
                    restoration_s.inventory.add(serializer.serializeItemStack(i));
                    iC++;
                }
                catch (IOException e)
                {
                }
            }
        }
        for (ItemStack i : value.armour) {
            if ((i instanceof ItemStack))
            {
                try
                {
                    restoration_s.armour.add(serializer.serializeItemStack(i));
                }
                catch (IOException e)
                {
                }
        }
      }
        restoration_s.enabled = value.enabled;
        restoration_s.level = value.level;
        restoration_s.exp = value.exp;
        return restoration_s;
    }
    
    public Restoration getRestoration(ReadyToSave value) throws IOException{
        StreamSerializer serializer;
        serializer = new StreamSerializer();
        Restoration restoration = new Restoration();
        restoration.inventory = new ItemStack[value.inventory.size()];
        restoration.armour = new ItemStack[value.armour.size()];
        for (int i = 0; i < value.inventory.size(); i++) {
            if ((value.inventory.get(i) instanceof String))
            {
                
                restoration.inventory[i] = serializer.deserializeItemStack(value.inventory.get(i));
             }
          }
        for (int i = 0; i < value.armour.size(); i++) {
            if ((value.armour.get(i) instanceof String))
            {
                restoration.armour[i] = serializer.deserializeItemStack(value.armour.get(i));
            }
        }
        restoration.enabled = value.enabled;
        restoration.level = value.level;
        restoration.exp = value.exp;
        return restoration;
    }
    
    public File getRestorationFile(String world, String playerName){
        File rf = new File(datafolder.getAbsolutePath()+File.separator+"inventories");
        rf.mkdirs();
        
        return new File(rf.getAbsoluteFile()+File.separator+"inventory."+playerName+"."+world+".inv");      
    }
    public boolean hasRestoration(String world, String playerName){
        return getRestorationFile(world,playerName).exists();
    }
    public void removeRestoration(Player pl, String world){
        File rf = getRestorationFile(world,pl.getName());
        if(rf.exists()) rf.delete();
    }
    public void saveInventory(Player pl, String world){
        // enderchest restoration manager
        ApatiteControler.em.saveEnderchest(pl, world);
        // inventory restoration manager
        File rf = getRestorationFile(world,pl.getName());
        if(rf.exists()) rf.delete();
        Restoration rt = new Restoration();
        rt.armour = pl.getInventory().getArmorContents();
        rt.inventory = pl.getInventory().getContents();
        rt.level = pl.getLevel();
        rt.exp = pl.getExp();
        try{
            rf.createNewFile();
            FileOutputStream os = new FileOutputStream(rf);
            ObjectOutputStream buffer = new ObjectOutputStream(os);
            buffer.writeObject(readyToSave(rt));
            pl.getInventory().clear();
            Utils.sendMsg(pl, ChatColor.GREEN+"Ваш инвентарь сохранён!");
        }  
        catch(IOException ex){}
    }
    public void restoreInventory(Player pl, String world){
      ApatiteControler.em.restoreEnderchest(pl, world);
      File rf = getRestorationFile(world,pl.getName());
      if(!rf.exists()) return;
      Restoration rs = null;
      try
      {
         FileInputStream fileIn = new FileInputStream(rf);
         ObjectInputStream in = new ObjectInputStream(fileIn);
         rs = getRestoration((ReadyToSave) in.readObject());
         in.close();
         fileIn.close();
      }catch(IOException i)
      {
         i.printStackTrace();
         return;
      }catch(ClassNotFoundException c){return;}
      pl.getInventory().clear();
      pl.getInventory().setContents(rs.inventory);
      pl.getInventory().setArmorContents(rs.armour);
      pl.setLevel(rs.level);
      pl.setExp(rs.exp);
      
      Utils.sendMsg(pl, ChatColor.GREEN+"Инвентарь успешно восстановлен!");
    }
}
