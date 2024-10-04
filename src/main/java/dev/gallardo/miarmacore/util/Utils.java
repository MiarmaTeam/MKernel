package dev.gallardo.miarmacore.util;

import de.tr7zw.nbtapi.NBTItem;
import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.MinepacksAccessor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dev.gallardo.miarmacore.util.Constants.*;

public class Utils {
	public static String placeholderParser(String message, List<String> placeholders, List<String> values) {
        int i = 0;
    	for(String p:placeholders) {
    		if(message.contains(p)) {
        	    message = message.replace(p, values.get(i));
        	    i++;
        	}
    	}
    	return message;
    }
	
	public static String colorCodeParser(String message) {
		message = MojangHEXParser(message).replace('&', '§');
		return message;
	}
	
	public static String MojangHEXParser(String input) {
       String hex = "&#[0-9A-Fa-f]{6}";
       Pattern pattern = Pattern.compile(hex);
       Matcher matcher = pattern.matcher(input);
       String res = null;
        
       if(matcher.find()) {
    	   String hexColor = matcher.group();
           String minecraftColor = convertHexToMinecraftColor(hexColor);
           res = input.replace(hexColor, minecraftColor);
       } else {
    	   res = input;
       }
       return res;
    }

	public static String convertHexToMinecraftColor(String hexColor) {
        // Extraer los valores R, G y B del formato HEX
        String r1 = hexColor.substring(2, 3);
        String r2 = hexColor.substring(3,4);
        String g1 = hexColor.substring(4, 5);
        String g2 = hexColor.substring(5, 6);
        String b1 = hexColor.substring(6,7);
        String b2 = hexColor.substring(7);

        // Construir el formato de color de Minecraft 
        return "&x&" + r1 + "&" + r2 + "&" + g1 + "&" + g2 + "&" + b1 + "&" + b2;
    }

	public static void copyResourceToFile(String resourceName, String destinationPath) throws IOException {
        ClassLoader classLoader = Utils.class.getClassLoader();
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
			File langs = new File(MiarmaCore.PLUGIN.getDataFolder(), fileName);
			langs.createNewFile();
			copyResourceToFile(fileName, new File(MiarmaCore.PLUGIN.getDataFolder(), fileName).getAbsolutePath());
		} catch (IOException e) {
			MiarmaCore.PLUGIN.getLogger().severe(MiarmaCore.CONFIG.getString("language.errors.langs"));
		}
	}

    public static String formatMessageNoColors(String message, boolean prefix){
        if(prefix)
            message = message.replace("[P]",MiarmaCore.CONFIG.getString("language.prefix"));
        return message;
    }

    public static String formatMessageNoColors(String message, boolean prefix, boolean placeholders, List<String> phs, List<String> values){
        if(prefix)
            message = message.replace("[P]",MiarmaCore.CONFIG.getString("language.prefix"));
        if(placeholders)
            message = placeholderParser(message, phs, values);
        return message;
    }

    public static String formatMessage(String message, boolean prefix){
        if(prefix)
            message = message.replace("[P]",MiarmaCore.CONFIG.getString("language.prefix"));
        return Utils.colorCodeParser(message);
    }

    public static String formatMessage(String message, boolean prefix, boolean placeholders, List<String> phs, List<String> values){
        if(placeholders)
            message = placeholderParser(message, phs, values);
        return formatMessage(message, prefix);
    }

    public static void sendMessage(String message, CommandSender sender, boolean prefix) {
        sender.sendMessage(formatMessage(message, prefix));
    }

    public static void sendMessage(String message, CommandSender sender, boolean prefix, boolean placeholders,
                                   List<String> phs, List<String> values) {
        sender.sendMessage(formatMessage(message, prefix, placeholders, phs, values));
    }

    public static String generateRandomPassword(int size) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~#$&*_+-=.";
        StringBuilder pass = new StringBuilder();
        for(int i = 0; i < size; i++) {
            int random = (int) (Math.random() * chars.length());
            pass.append(chars.charAt(random));
        }
        return pass.toString();
    }

    public static String getKey(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.getString("specialItem") != null) {
            return nbtItem.getString("specialItem");
        }

        List<Recipe> matchingRecipes = Bukkit.getRecipesFor(item);
        for (Recipe recipe : matchingRecipes) {
            if (recipe instanceof ShapedRecipe) {
                return ((ShapedRecipe) recipe).getKey().getKey();
            }

            if (recipe instanceof ShapelessRecipe) {
                return ((ShapelessRecipe) recipe).getKey().getKey();
            }

            if (recipe instanceof FurnaceRecipe) {
                return ((FurnaceRecipe) recipe).getKey().getKey();
            }

            if (recipe instanceof BlastingRecipe) {
                return ((BlastingRecipe) recipe).getKey().getKey();
            }

            if (recipe instanceof CampfireRecipe) {
                return ((CampfireRecipe) recipe).getKey().getKey();
            }

            if (recipe instanceof SmokingRecipe) {
                return ((SmokingRecipe) recipe).getKey().getKey();
            }

            if (recipe instanceof StonecuttingRecipe) {
                return ((StonecuttingRecipe) recipe).getKey().getKey();
            }

            if (recipe instanceof SmithingRecipe) {
                return ((SmithingRecipe) recipe).getKey().getKey();
            }

            if (recipe instanceof CookingRecipe) {
                return ((CookingRecipe) recipe).getKey().getKey();
            }

        }
        return null;
    }

    public static Material getMaterialWithProb() {
        double n = Math.random();
        Material res = null;
        if(n>0.40) {
            res = Material.BEEF;
        } else {
            res = Material.BONE;
        }
        return res;
    }

    public static void refillItem(Player player, Material material, EquipmentSlot hand) {
        ItemStack[] items = player.getInventory().getStorageContents();

        for (int i = 0; i < 36; ++i) {
            if (items[i] != null && isValidSlot(i, player) && items[i].getType().equals(material)) {
                if (hand.equals(EquipmentSlot.HAND)) {
                    player.getInventory().setItemInMainHand(items[i]);
                    player.getInventory().setItem(i, (ItemStack) null);
                    break;
                }

                if (hand.equals(EquipmentSlot.OFF_HAND)) {
                    player.getInventory().setItemInOffHand(items[i]);
                    player.getInventory().setItem(i, (ItemStack) null);
                    break;
                }
            }
        }

    }

    public static void refillItemFromMinepack(Player player, Material material, EquipmentSlot hand) {
        Inventory backpack = MinepacksAccessor.getPlayerBackpackInventory(player);

        if (backpack != null) {
            ItemStack[] contents = backpack.getContents();
            for (int i = 0; i < contents.length; i++) {
                ItemStack itemStack = contents[i];
                if (itemStack != null && isValidSlot(i, player) && itemStack.getType() == material) {
                    if (hand == EquipmentSlot.HAND) {
                        player.getInventory().setItemInMainHand(itemStack);
                    } else if (hand == EquipmentSlot.OFF_HAND) {
                        player.getInventory().setItemInOffHand(itemStack);
                    }
                    contents[i] = (ItemStack) null;
                    backpack.setContents(contents);
                    break;
                }
            }
        }
    }

    public static boolean isValidSlot(int i, Player player) {
        return i != player.getInventory().getHeldItemSlot() && i != 40;
    }

    public static int getItemCount(Inventory inventory, Material material) {
        int count = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == material) {
                count += item.getAmount();
            }
        }
        return count;
    }

    public static void reloadConfigItem(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() != Material.PAPER) {
            return;
        }

        ItemMeta itemMeta = clickedItem.getItemMeta();
        if (itemMeta == null || !itemMeta.hasDisplayName()) {
            return;
        }

        String displayName = itemMeta.getDisplayName();
        String configKey = "config.modules." + ChatColor.stripColor(displayName);

        boolean currentValue = MiarmaCore.CONFIG.getBoolean(configKey);
        boolean newValue = !currentValue;

        MiarmaCore.CONFIG.getConfig().set(configKey, newValue);
        MiarmaCore.CONFIG.save();

        itemMeta.setLore(List.of(Utils.colorCodeParser(
                MiarmaCore.CONFIG.getString("language.inventories.configMenu.valueLore")) + newValue));
        clickedItem.setItemMeta(itemMeta);

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }

        event.setCancelled(true);
        event.getInventory().setItem(event.getSlot(), clickedItem);
    }

    public static double distance(Player p1, Player p2) {
        return p1.getLocation().distance(p2.getLocation());
    }

    public static void saveInventory(Player p) throws IOException{
        File f = new File(MiarmaCore.PLUGIN.getDataFolder().getAbsolutePath(), "inventories/"
                + p.getName() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        c.set("inventory", p.getInventory().getContents());
        c.save(f);
    }

    @SuppressWarnings("unchecked")
    public static int restoreInventory(Player p) throws IOException {
        File f = new File(MiarmaCore.PLUGIN.getDataFolder().getAbsolutePath(), "inventories/"
                + p.getName() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        ItemStack[] content = ((List<ItemStack>) c.get("inventory")).toArray(new ItemStack[0]);
        p.getInventory().setContents(content);
        return content.length;
    }

    public static void clearInventory(Player p) {
        File f = new File(MiarmaCore.PLUGIN.getDataFolder().getAbsolutePath(), "inventories/"
                + p.getName() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        c.set("inventories", null);
    }

    public static long cooldownToMillis(String s) {
        if(s.contains("s"))
            return Long.parseLong(s.replace("s", "")) * 1000;
        if(s.contains("m"))
            return Long.parseLong(s.replace("m", "")) * 60 * 1000;
        if(s.contains("h"))
            return Long.parseLong(s.replace("h", "")) * 3600 * 1000;
        if(s.contains("d"))
            return Long.parseLong(s.replace("d", "")) * 86400 * 1000;
        return 0;
    }

    public static String millisToCooldown(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        if(days > 0)
            return days + "d";
        if(hours > 0)
            return hours + "h";
        if(minutes > 0)
            return minutes + "m";
        return seconds + "s";
    }


}
