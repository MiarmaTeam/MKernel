package net.miarma.mkernel.listeners;

import de.tr7zw.nbtapi.NBTItem;
import net.miarma.mkernel.common.annotation.Version;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static net.miarma.mkernel.common.Constants.SPECIAL_ITEM_TAG;
import static net.miarma.mkernel.common.Constants.ZOMBIFICATION_POTION_KEY;

public class PotionListener implements Listener {
    
    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        ItemStack potionItem = event.getPotion().getItem();
        NBTItem nbtItem = new NBTItem(potionItem);
        String specialTag = nbtItem.getString(SPECIAL_ITEM_TAG);

        if (!ZOMBIFICATION_POTION_KEY.equals(specialTag)) return;

        for (LivingEntity entity : event.getAffectedEntities()) {
            if (entity instanceof Villager villager) {
                int roll = (int) (Math.random() * 100);
                if (roll >= 30) {
                    villager.zombify();
                } else {
                    villager.setHealth(0.0);
                }
            }

            if (entity instanceof Player player) {
                player.addPotionEffect(new PotionEffect(
                        PotionEffectType.POISON, 20 * 60, 3)); // 60s, nivel 3
            }
        }
    }

}
