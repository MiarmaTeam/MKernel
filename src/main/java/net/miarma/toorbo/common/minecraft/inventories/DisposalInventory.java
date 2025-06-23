package net.miarma.toorbo.common.minecraft.inventories;

import net.miarma.toorbo.config.providers.MessageProvider;
import net.miarma.toorbo.util.MessageUtil;
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
