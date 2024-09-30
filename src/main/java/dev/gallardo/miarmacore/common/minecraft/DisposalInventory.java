package dev.gallardo.miarmacore.common.minecraft;

import dev.gallardo.miarmacore.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import static dev.gallardo.miarmacore.util.Constants.*;

public class DisposalInventory {
    private static Inventory inv;

    public DisposalInventory() {
    }

    public static Inventory getInv() {
        return inv;
    }

    static {
        inv = Bukkit.createInventory(null, 54,
                Utils.colorCodeParser(CONFIG.getString("language.inventories.disposal.title")));
    }
}
