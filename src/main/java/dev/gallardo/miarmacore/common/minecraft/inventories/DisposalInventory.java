package dev.gallardo.miarmacore.common.minecraft.inventories;

import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;


public class DisposalInventory {
    private static Inventory inv;

    public DisposalInventory() {
    }

    public static Inventory getInv() {
        return inv;
    }

    static {
        inv = Bukkit.createInventory(null, 54,
                MessageUtil.parseColors(MessageProvider.Inventories.getDisposalTitle()));
    }
}
