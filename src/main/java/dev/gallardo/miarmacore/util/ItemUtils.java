package dev.gallardo.miarmacore.util;

import de.tr7zw.nbtapi.NBTItem;
import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtils {
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
                return ((CookingRecipe<?>) recipe).getKey().getKey();
            }

        }
        return null;
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

        itemMeta.setLore(List.of(MessageUtils.parseColors(
                MessageProvider.Inventories.getConfigMenuValueLore()) + newValue));
        clickedItem.setItemMeta(itemMeta);

        if (event.getWhoClicked() instanceof Player player) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }

        event.setCancelled(true);
        event.getInventory().setItem(event.getSlot(), clickedItem);
    }

    public static Material getBeefBoneWithProb() {
        double n = Math.random();
        Material res;
        if(n>0.40) {
            res = Material.BEEF;
        } else {
            res = Material.BONE;
        }
        return res;
    }
}
