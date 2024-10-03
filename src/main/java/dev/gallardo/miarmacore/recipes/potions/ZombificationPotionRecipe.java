package dev.gallardo.miarmacore.recipes.potions;

import de.tr7zw.nbtapi.NBTItem;
import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.Collections;

public class ZombificationPotionRecipe {
	private static ItemStack crear() {
		ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        
        ItemMeta meta = potion.getItemMeta();
        meta.setDisplayName(Utils.colorCodeParser(
                MiarmaCore.CONFIG.getString("language.items.zombificationPotion.name")));
        meta.setLore(Collections.singletonList(Utils.colorCodeParser(
                MiarmaCore.CONFIG.getString("language.items.zombificationPotion.lore"))));
        meta.addEnchant(Enchantment.MENDING, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        
        potion.setItemMeta(meta);
        
        NBTItem nbtItem = new NBTItem(potion);
        nbtItem.setString("specialItem", "zombification_potion");

        RECIPES.add(nbtItem.getItem());
        return nbtItem.getItem();
    }
    
    public static ShapedRecipe get() {
    	ItemStack potion = crear();
    	
        NamespacedKey zombificationKey = new NamespacedKey(MiarmaCore.PLUGIN, "zombification_potion");
        ShapedRecipe recipe = new ShapedRecipe(zombificationKey, potion);
        recipe.shape(
        		" Z ", 
        		" P ", 
        		"   ");
        recipe.setIngredient('Z', Material.ZOMBIE_HEAD);
        recipe.setIngredient('P', Material.SPLASH_POTION);
        return recipe;
    }
}
