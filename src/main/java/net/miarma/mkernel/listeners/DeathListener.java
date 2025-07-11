package net.miarma.mkernel.listeners;

import net.miarma.mkernel.common.annotation.Version;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener {
    
    @EventHandler
    public void onZombieDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        EntityType type = event.getEntityType();
        if (type != EntityType.ZOMBIE && type != EntityType.ZOMBIE_VILLAGER) return;

        ItemStack weapon = killer.getInventory().getItemInMainHand();
        if (weapon == null || weapon.getType() == Material.AIR || !weapon.hasItemMeta()) return;

        boolean hasLooting = weapon.containsEnchantment(Enchantment.LOOTING);
        int chance = hasLooting ? 70 : 50;
        int roll = (int) (Math.random() * 100);

        if (roll < chance) {
            Location loc = event.getEntity().getLocation();
            ItemStack head = new ItemStack(Material.ZOMBIE_HEAD);
            event.getEntity().getWorld().dropItem(loc, head);
        }
    }
}
