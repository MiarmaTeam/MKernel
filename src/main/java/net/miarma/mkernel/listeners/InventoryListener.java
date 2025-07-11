package net.miarma.mkernel.listeners;

import net.miarma.mkernel.common.minecraft.inventories.DisposalInventory;
import net.miarma.mkernel.common.minecraft.inventories.GlobalChest;
import net.miarma.mkernel.common.minecraft.inventories.InvseeInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!event.getInventory().equals(GlobalChest.getInv())) return;
        GlobalChest.loadChest();
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();

        // Cierre del GlobalChest
        if (inv.equals(GlobalChest.getInv())) {
            GlobalChest.saveChest();
            return;
        }

        // Cierre de la papelera temporal
        if (inv.equals(DisposalInventory.getInv())) {
            inv.clear(); // Más limpio que llamar de nuevo a getInv()
            return;
        }

        // Cierre del inventario de invsee
        if (!InvseeInventory.isInvseeInventory(inv)) return;

        Player admin = (Player) event.getPlayer();
        Player target = InvseeInventory.adminPlayerMap.remove(admin);
        if (target == null) return;

        PlayerInventory targetInv = target.getInventory();

        // Inventario menos armadura
        for (int i = 0; i < 36; i++) {
            targetInv.setItem(i, inv.getItem(i));
        }

        // Armadura
        targetInv.setHelmet(inv.getItem(36));
        targetInv.setChestplate(inv.getItem(37));
        targetInv.setLeggings(inv.getItem(38));
        targetInv.setBoots(inv.getItem(39));
        targetInv.setItemInOffHand(inv.getItem(40));
    }

}
