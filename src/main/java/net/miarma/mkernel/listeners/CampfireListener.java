package net.miarma.mkernel.listeners;

import net.miarma.mkernel.common.annotation.Version;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CampfireStartEvent;

public class CampfireListener implements Listener {
    
    @EventHandler
    public void onCampfireCook(CampfireStartEvent event) {
        if(!event.getBlock().getBlockData().getMaterial().equals(Material.SOUL_CAMPFIRE)) {
            event.setTotalCookTime((int)Math.floor(1.5*event.getTotalCookTime()));
        }
    }
}
