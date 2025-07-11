package net.miarma.mkernel.recipes.tools;

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

import java.util.Collections;

import static net.miarma.mkernel.common.Constants.*;

public class ScissorsRecipe {
	private static ItemStack crear() {
        ItemStack scissors = new ItemStack(Material.SHEARS);

        ItemMeta meta = scissors.getItemMeta();
        meta.setDisplayName(MessageUtil.parseColors(
                MessageProvider.Items.getScissorsName()));
        meta.setLore(Collections.singletonList(MessageUtil.parseColors(
                MessageProvider.Items.getScissorsLore())));
        meta.addEnchant(Enchantment.UNBREAKING, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                
        scissors.setItemMeta(meta);
        
        NBTItem nbtItem = new NBTItem(scissors);
        nbtItem.setString(SPECIAL_ITEM_TAG, SCISSORS_KEY);

        RECIPES.add(nbtItem.getItem());
        return nbtItem.getItem();
    }
    
    public static ShapedRecipe get() {
    	ItemStack scissors = crear();
        NamespacedKey scissorsRecipeKey = new NamespacedKey(MKernel.PLUGIN, SCISSORS_KEY);
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
