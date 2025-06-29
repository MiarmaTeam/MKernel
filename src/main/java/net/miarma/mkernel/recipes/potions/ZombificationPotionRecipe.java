package net.miarma.mkernel.recipes.potions;

import de.tr7zw.nbtapi.NBTItem;
import net.miarma.mkernel.MKernel;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import static net.miarma.mkernel.util.Constants.*;

import java.util.Collections;

public class ZombificationPotionRecipe {
	private static ItemStack crear() {
		ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        
        ItemMeta meta = potion.getItemMeta();
        meta.setDisplayName(MessageUtil.parseColors(
                MessageProvider.Items.getZombificationPotionName()));
        meta.setLore(Collections.singletonList(MessageUtil.parseColors(
                MessageProvider.Items.getZombificationPotionLore())));
        meta.addEnchant(Enchantment.MENDING, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        
        potion.setItemMeta(meta);
        
        NBTItem nbtItem = new NBTItem(potion);
        nbtItem.setString(SPECIAL_ITEM_TAG, ZOMBIFICATION_POTION_KEY);

        RECIPES.add(nbtItem.getItem());
        return nbtItem.getItem();
    }
    
    public static ShapedRecipe get() {
    	ItemStack potion = crear();
    	
        NamespacedKey zombificationKey = new NamespacedKey(MKernel.PLUGIN, ZOMBIFICATION_POTION_KEY);
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
