package dev.gallardo.miarmacore.common.minecraft;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

import static dev.gallardo.miarmacore.util.Constants.*;

public class GlobalChest {
    private static File itemsFile;
    private static FileConfiguration items;
    private static Inventory inv;

    public GlobalChest() {
    }

    public static File getItemsFile() {
        return itemsFile;
    }

    public static FileConfiguration getItems() {
        return items;
    }

    public static Inventory getInv() {
        return inv;
    }

    public static void loadChest() {
        ConfigurationSection inventorySection = items.getConfigurationSection("inventory");
        if (inventorySection != null && inventorySection.getList("items") != null) {
            inv.setContents(inventorySection.getList("items").toArray(value -> new ItemStack[value]));
        }

    }

    public static void saveChest() {
        ConfigurationSection inventorySection = items.createSection("inventory");
        inventorySection.set("items", inv.getContents());

        try {
            items.save(itemsFile);
        } catch (IOException e) {
            MiarmaCore.LOGGER.severe("Error saving items.yml file!");
        }

    }

    public static void loadConfig() {
        itemsFile = new File(MiarmaCore.PLUGIN.getDataFolder(), "items.yml");
        items = YamlConfiguration.loadConfiguration(itemsFile);
    }

    static {
        inv = Bukkit.createInventory(null, 54,
                Utils.colorCodeParser(MessageProvider.Inventories.getGlobalChestTitle()));
    }
}
