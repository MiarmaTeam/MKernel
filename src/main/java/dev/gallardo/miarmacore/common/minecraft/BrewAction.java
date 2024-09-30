package dev.gallardo.miarmacore.common.minecraft;

import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

public interface BrewAction {
	public void brew(BrewerInventory inventory, ItemStack item, ItemStack ingredient);
}
