package net.miarma.mkernel.recipes.tools;

import de.tr7zw.nbtapi.NBTItem;
import net.miarma.mkernel.MKernel;
import net.miarma.mkernel.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

import static net.miarma.mkernel.common.Constants.*;

public class SpawnerBreakerRecipe {
	private static ItemStack crear() {
        ItemStack spawnerBreaker = new ItemStack(Material.GOLDEN_PICKAXE);
        
        ItemMeta meta = spawnerBreaker.getItemMeta();  
        meta.setDisplayName(MessageUtil.parseColors(MKernel.CONFIG
        		.getString("language.items.spawnerBreaker.name")));
        meta.setLore(Collections.singletonList(MessageUtil.parseColors(MKernel.CONFIG
        		.getString("language.items.spawnerBreaker.lore"))));
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
               
        spawnerBreaker.setItemMeta(meta);
        
        NBTItem nbtItem = new NBTItem(spawnerBreaker);
        nbtItem.setString(SPECIAL_ITEM_TAG, SPAWNER_BREAKER_KEY);
        
        RECIPES.add(nbtItem.getItem());
        return nbtItem.getItem();
    }
    
    public static ShapedRecipe get() {
    	ItemStack spawnerBreaker = crear();
        NamespacedKey spawnerBreakerRecipeKey = new NamespacedKey(MKernel.PLUGIN, SPAWNER_BREAKER_KEY);
        ShapedRecipe spawnerBreakerRecipe = new ShapedRecipe(spawnerBreakerRecipeKey, spawnerBreaker);
        spawnerBreakerRecipe.shape(
        		" N ", 
        		" P ", 
        		" D ");
        spawnerBreakerRecipe.setIngredient('N', Material.NETHERITE_INGOT);
        spawnerBreakerRecipe.setIngredient('P', Material.GOLDEN_PICKAXE);
        spawnerBreakerRecipe.setIngredient('D', Material.DIAMOND);
        return spawnerBreakerRecipe;
    }
}
