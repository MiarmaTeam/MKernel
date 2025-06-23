package net.miarma.toorbo.recipes.misc;

import static net.miarma.toorbo.util.Constants.*;

import net.miarma.toorbo.Toorbo;
import net.miarma.toorbo.config.providers.ConfigProvider;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.recipe.CookingBookCategory;

public class RottenFleshCampfireRecipe {
    public static CampfireRecipe get() {
        NamespacedKey rottenRecipeKey = new NamespacedKey(Toorbo.PLUGIN, ROTTEN_CAMFIRE_KEY);
        CampfireRecipe rottenRecipe = new CampfireRecipe(
        		rottenRecipeKey, 
        		new ItemStack(Material.BEEF), 
        		Material.ROTTEN_FLESH, 
        		0, ConfigProvider.Values.getRottenFleshCookTime() * 20);
        rottenRecipe.setCategory(CookingBookCategory.FOOD);
        return rottenRecipe;
    }

}
