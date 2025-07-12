package net.miarma.mkernel.listeners;

import net.miarma.mkernel.common.annotation.Version;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.HappyGhast;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class HappyGhastListener implements Listener {

    @Version("1.21.6")
    @EventHandler
    public void onHappyGhastSpawn(EntitySpawnEvent event) {
        if(event.getEntity() instanceof HappyGhast happyGhast) {
            AttributeInstance flyingSpeed = happyGhast.getAttribute(Attribute.FLYING_SPEED);
            if(flyingSpeed != null) {
                flyingSpeed.setBaseValue(0.1);
            }
        }
    }

}
