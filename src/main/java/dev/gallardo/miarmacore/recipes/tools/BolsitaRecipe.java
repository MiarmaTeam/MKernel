package dev.gallardo.miarmacore.recipes.tools;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

import static dev.gallardo.miarmacore.util.Constants.*;

public class BolsitaRecipe {

    private static ItemStack crear() {
        ItemStack bolsita = new ItemStack(Material.BUNDLE, 1);

        ItemMeta meta = bolsita.getItemMeta();
        meta.setDisplayName(Utils.colorCodeParser(
                CONFIG.getString("language.items.bolsita.name")));
        meta.setLore(Collections.singletonList(Utils.colorCodeParser(
                CONFIG.getString("language.items.bolsita.lore"))));
        bolsita.setItemMeta(meta);

        return bolsita;
    }

    public static ShapelessRecipe get() {
        ItemStack bolsita = crear();
        NamespacedKey bolsitaRecipeKey = new NamespacedKey(MiarmaCore.PLUGIN, "bolsita");
        ShapelessRecipe bolsitaRecipe = new ShapelessRecipe(bolsitaRecipeKey, bolsita);
        bolsitaRecipe.addIngredient(1, Material.LEATHER);
        bolsitaRecipe.addIngredient(1, Material.STRING);
        return bolsitaRecipe;
    }

}
