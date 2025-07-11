package net.miarma.mkernel.listeners;

import de.tr7zw.nbtapi.NBTItem;
import net.miarma.mkernel.common.annotation.Version;
import net.miarma.mkernel.common.minecraft.events.EntityEventHelper;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import static net.miarma.mkernel.common.Constants.SCISSORS_KEY;
import static net.miarma.mkernel.common.Constants.SPECIAL_ITEM_TAG;

public class EntityListener implements Listener {
    
    @EventHandler
    public void onEntityRightClick(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        Entity target = event.getRightClicked();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR || item.getAmount() == 0) return;

        EntityEventHelper helper = EntityEventHelper.of(event);
        NBTItem nbtItem = new NBTItem(item);
        String specialType = nbtItem.getString(SPECIAL_ITEM_TAG);

        EntityType type = target.getType();

        if (SCISSORS_KEY.equals(specialType)) {
            switch (type) {
                case PIG -> helper.handleScissorsOnPig();
                case COW -> helper.handleScissorsOnCow();
                case RABBIT -> helper.handleScissorsOnRabbit();
                case CHICKEN -> helper.handleScissorsOnChicken();
                case CREEPER -> helper.handleScissorsOnCreeper();
                case ZOMBIE -> helper.handleScissorsOnZombie();
                default -> { }
            }
            return;
        }

        // Acciones por tipo de ítem (no por NBT)
        switch (type) {
            case SKELETON -> {
                if (item.getType() == Material.ROTTEN_FLESH) {
                    helper.handleRottenFleshOnSkeleton();
                }
            }
            case PILLAGER -> {
                if (item.getType() == Material.TOTEM_OF_UNDYING) {
                    helper.handleTotemOnPillager();
                }
            }
            default -> { }
        }
    }

}
