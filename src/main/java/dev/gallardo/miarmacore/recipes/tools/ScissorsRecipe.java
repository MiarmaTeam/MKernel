package dev.gallardo.miarmacore.recipes.tools;

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

import java.util.Collections;

import static dev.gallardo.miarmacore.util.Constants.*;

public class ScissorsRecipe {
	private static ItemStack crear() {
        ItemStack scissors = new ItemStack(Material.SHEARS);

        ItemMeta meta = scissors.getItemMeta();
        meta.setDisplayName(Utils.colorCodeParser(
                MiarmaCore.CONFIG.getString("language.items.scissors.name")));
        meta.setLore(Collections.singletonList(Utils.colorCodeParser(
                MiarmaCore.CONFIG.getString("language.items.scissors.lore"))));
        meta.addEnchant(Enchantment.UNBREAKING, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                
        scissors.setItemMeta(meta);
        
        NBTItem nbtItem = new NBTItem(scissors);
        nbtItem.setString("specialItem", "scissors");

        RECIPES.add(nbtItem.getItem());
        return nbtItem.getItem();
    }
    
    public static ShapedRecipe get() {
    	ItemStack scissors = crear();
        NamespacedKey scissorsRecipeKey = new NamespacedKey(MiarmaCore.PLUGIN, "scissors");
        ShapedRecipe scissorsRecipe = new ShapedRecipe(scissorsRecipeKey, scissors);
        scissorsRecipe.shape(
        		" D ", 
        		"DSD", 
        		" D ");
        scissorsRecipe.setIngredient('D', Material.DIAMOND);
        scissorsRecipe.setIngredient('S', Material.SHEARS);
        return scissorsRecipe;
    }
}
