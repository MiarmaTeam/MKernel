package dev.gallardo.miarmacore.recipes.tools;

import de.tr7zw.nbtapi.NBTItem;
import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

import static dev.gallardo.miarmacore.util.Constants.*;

public class AdminStickRecipe {
	public static ItemStack crear() {
        ItemStack stick = new ItemStack(Material.STICK);
       
        ItemMeta meta = stick.getItemMeta();
        meta.setDisplayName(Utils.colorCodeParser(
                MiarmaCore.CONFIG.getString("language.items.adminStick.name")));
        meta.setLore(Collections.singletonList(Utils.colorCodeParser(
                MiarmaCore.CONFIG.getString("language.items.adminStick.lore"))));
        
        stick.setItemMeta(meta);
        
        NBTItem nbtItem = new NBTItem(stick);
        nbtItem.setString("specialItem", "ADMIN_STICK");

        RECIPES.add(nbtItem.getItem());
        return nbtItem.getItem();
    }
    
    public static ShapedRecipe get() {
    	ItemStack palo = crear();
        NamespacedKey paloRecipeKey = new NamespacedKey(MiarmaCore.PLUGIN, "admin_stick");
        ShapedRecipe paloRecipe = new ShapedRecipe(paloRecipeKey, palo);
        paloRecipe.shape(
        		"DDD", 
        		"DSD", 
        		"DDD");
        paloRecipe.setIngredient('D', Material.BEDROCK);
        paloRecipe.setIngredient('S', Material.STICK);
        return paloRecipe;
    }
    
    
}

