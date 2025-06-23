package net.miarma.toorbo.recipes.tools;

import de.tr7zw.nbtapi.NBTItem;
import net.miarma.toorbo.Toorbo;
import net.miarma.toorbo.config.providers.MessageProvider;
import net.miarma.toorbo.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

import static net.miarma.toorbo.util.Constants.*;

public class BolsitaRecipe {

    private static ItemStack crear() {
        ItemStack bolsita = new ItemStack(Material.BUNDLE, 1);

        ItemMeta meta = bolsita.getItemMeta();
        meta.setDisplayName(MessageUtil.parseColors(
                MessageProvider.Items.getBolsitaName()));
        meta.setLore(Collections.singletonList(MessageUtil.parseColors(
                MessageProvider.Items.getBolsitaLore())));
        bolsita.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(bolsita);
        nbtItem.setString(SPECIAL_ITEM_TAG, BOLSITA_KEY);

        RECIPES.add(bolsita);
        return nbtItem.getItem();
    }

    public static ShapelessRecipe get() {
        ItemStack bolsita = crear();
        NamespacedKey bolsitaRecipeKey = new NamespacedKey(Toorbo.PLUGIN, BOLSITA_KEY);
        ShapelessRecipe bolsitaRecipe = new ShapelessRecipe(bolsitaRecipeKey, bolsita);
        bolsitaRecipe.addIngredient(1, Material.LEATHER);
        bolsitaRecipe.addIngredient(1, Material.STRING);
        return bolsitaRecipe;
    }

}
