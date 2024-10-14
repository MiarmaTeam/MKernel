package dev.gallardo.miarmacore.util;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.teleport.TpaRequests;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static TpaRequests TPA_REQUESTS = TpaRequests.getInstance();
    public static List<ItemStack> RECIPES = new ArrayList<>();
    public static NamespacedKey VANISH_KEY = new NamespacedKey(MiarmaCore.PLUGIN, "vanish");
    public static NamespacedKey SPY_KEY = new NamespacedKey(MiarmaCore.PLUGIN, "spy");
    public static NamespacedKey FROZEN_KEY = new NamespacedKey(MiarmaCore.PLUGIN, "frozen");
}
