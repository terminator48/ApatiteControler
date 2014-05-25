/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apatitecontroler.ApatiteScavenger.enderchest;

import apatitecontroler.Utils;
import com.comphenix.protocol.utility.StreamSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Daniil
 */
public class EnderchestManager {

    public File datafolder = null;
    private Object quarks;

    public EnderchestManager(File dataFolder) {
        datafolder = dataFolder;
    }

    public Map convertStr2Map(String str) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(str, str);
        return map;
    }

    public enderchest serialize(ItemStack[] enderchest) {
        StreamSerializer serializer;
        serializer = new StreamSerializer();
        int iC = 0;

        enderchest restoration_s = new enderchest();
        
        for (ItemStack i : enderchest) {
            if ((i instanceof ItemStack)) {
                try {
                    restoration_s.enderchest.add(serializer.serializeItemStack(i));
                    iC++;
                } catch (IOException e) {
                }
            }
        }
        return restoration_s;
    }

    public ItemStack[] deserialize(enderchest value) throws IOException {
        StreamSerializer serializer;
        serializer = new StreamSerializer();
        ItemStack[] result = new ItemStack[value.enderchest.size()]; 
        for (int i = 0; i < value.enderchest.size(); i++) {
            if ((value.enderchest.get(i) instanceof String)) {

                result[i] = serializer.deserializeItemStack(value.enderchest.get(i));
            }
        }
        return result;
    }

    public File getRestorationFile(String world, String playerName) {
        File rf = new File(datafolder.getAbsolutePath() + File.separator + "inventories");
        rf.mkdirs();
        return new File(rf.getAbsoluteFile() + File.separator + "enderchest." + playerName + "." + world + ".inv");
    }

    public boolean hasRestoration(String world, String playerName) {
        return getRestorationFile(world, playerName).exists();
    }

    public void saveEnderchest(Player pl, String world) {
        File rf = getRestorationFile(world, pl.getName());
        if (rf.exists()) {
            rf.delete();
        }
        try {
            rf.createNewFile();
            FileOutputStream os = new FileOutputStream(rf);
            ObjectOutputStream buffer = new ObjectOutputStream(os);
            buffer.writeObject(serialize(pl.getEnderChest().getContents()));
            pl.getEnderChest().clear();
        } catch (IOException ex) {
        }
    }

    public void restoreEnderchest(Player pl, String world) {
        File rf = getRestorationFile(world, pl.getName());
        if (!rf.exists()) {
            return;
        }
        ItemStack[] enderchest = null;
        try {
            FileInputStream fileIn = new FileInputStream(rf);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            enderchest = deserialize((enderchest) in.readObject());
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            return;
        }
        pl.getEnderChest().setContents(enderchest);
    }
}
