package net.miarma.mkernel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.function.Predicate;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.miarma.mkernel.MKernel;
import net.miarma.mkernel.config.providers.MessageProvider;

public class FileUtil {
    public static void copyResourceToFile(String resourceName, String destinationPath) throws IOException {
        ClassLoader classLoader = MessageUtil.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(resourceName);
             FileOutputStream outputStream = new FileOutputStream(destinationPath)) {

            byte[] buffer = new byte[1024];
            int length;
            while (true) {
                assert inputStream != null;
                if ((length = inputStream.read(buffer)) == -1) break;
                outputStream.write(buffer, 0, length);
            }
        }
    }

    public static void createLangs(String fileName) {
        try {
            File langs = new File(MKernel.PLUGIN.getDataFolder(), fileName);
            langs.createNewFile();
            copyResourceToFile(fileName, new File(MKernel.PLUGIN.getDataFolder(), fileName).getAbsolutePath());
        } catch (IOException e) {
            MKernel.PLUGIN.getLogger().severe(MessageProvider.Errors.failedCreatingLangs());
        }
    }

    public static void saveInventory(Player p) throws IOException{
        File f = new File(MKernel.PLUGIN.getDataFolder().getAbsolutePath(), "inventories/"
                + p.getName() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        PlayerInventory inv = p.getInventory();
        
        c.set("helmet", inv.getHelmet());
        c.set("chestplate", inv.getChestplate());
        c.set("leggings", inv.getLeggings());
        c.set("boots", inv.getBoots());
        c.set("offhand", inv.getItemInOffHand());
        
        for(int i = 0; i < inv.getSize(); i++) {
        	ItemStack item = inv.getItem(i);
        	if(item != null && item.getType() != Material.AIR) {
        		c.set("slot_" + i, item);
        	}
        }
        
        c.save(f);
    }

    public static int restoreInventory(Player p) throws IOException {
    	Predicate<ItemStack> pred = item -> item != null && item.getType() != Material.AIR;
        File f = new File(MKernel.PLUGIN.getDataFolder().getAbsolutePath(), "inventories/" + p.getName() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        PlayerInventory inv = p.getInventory();
        
        inv.clear();
        
        // Armadura
        inv.setHelmet(c.getItemStack("helmet"));
        inv.setChestplate(c.getItemStack("chestplate"));
        inv.setLeggings(c.getItemStack("leggings"));
        inv.setBoots(c.getItemStack("boots"));
        
        int totalItems = 0;
        
        // Resto
        for(int i = 0; i < inv.getSize(); i++) {
        	ItemStack item = c.getItemStack("slot_" + i);
        	if(pred.test(item)) {
        		inv.setItem(i, item);
        		totalItems += item.getAmount();
        	}
        }
        
        return totalItems;
        
    }

    public static void clearInventory(Player p) throws IOException {
        File f = new File(MKernel.PLUGIN.getDataFolder().getAbsolutePath(), "inventories/"
                + p.getName() + ".yml");
        Files.delete(f.toPath());
    }
}
