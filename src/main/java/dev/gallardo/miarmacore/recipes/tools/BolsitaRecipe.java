package dev.gallardo.miarmacore.recipes.tools;

import de.tr7zw.nbtapi.NBTItem;
import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
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
                MessageProvider.Items.getBolsitaName()));
        meta.setLore(Collections.singletonList(Utils.colorCodeParser(
                MessageProvider.Items.getBolsitaLore())));
        bolsita.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(bolsita);
        nbtItem.setString("specialItem", "bolsita");

        RECIPES.add(bolsita);
        return nbtItem.getItem();
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
