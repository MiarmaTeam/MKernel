package net.miarma.mkernel.recipes.tools;

import de.tr7zw.nbtapi.NBTItem;
import net.miarma.mkernel.MKernel;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

import static net.miarma.mkernel.common.Constants.*;

public class AdminStickRecipe {
	public static ItemStack crear() {
        ItemStack stick = new ItemStack(Material.STICK);
       
        ItemMeta meta = stick.getItemMeta();
        meta.setDisplayName(MessageUtil.parseColors(
                MessageProvider.Items.getAdminStickName()));
        meta.setLore(Collections.singletonList(MessageUtil.parseColors(
                MessageProvider.Items.getAdminStickLore())));
        
        stick.setItemMeta(meta);
        
        NBTItem nbtItem = new NBTItem(stick);
        nbtItem.setString(SPECIAL_ITEM_TAG, ADMIN_STICK_KEY);

        RECIPES.add(nbtItem.getItem());
        return nbtItem.getItem();
    }
    
    public static ShapedRecipe get() {
    	ItemStack palo = crear();
        NamespacedKey paloRecipeKey = new NamespacedKey(MKernel.PLUGIN, ADMIN_STICK_KEY);
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

