package net.miarma.mkernel.listeners;

import net.miarma.mkernel.MKernel;
import net.miarma.mkernel.common.annotation.Version;
import net.miarma.mkernel.config.CustomConfigManager;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.tasks.LocationTrackerTask;
import net.miarma.mkernel.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public class WorldListener implements Listener {
    
    @EventHandler
    public void onBlockedWorldEnter(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String newWorld = player.getWorld().getName();

        CustomConfigManager config = new CustomConfigManager(MKernel.PLUGIN, "blockedWorlds.yml");
        config.reloadConfig();

        List<String> blockedWorlds = config.getConfig().getStringList("blockedWorlds");

        if (!blockedWorlds.contains(newWorld)) return;

        Location fallback = LocationTrackerTask.getPlayerRealTimeLocation(player);
        if (fallback != null) {
            Location safeLocation = fallback.clone().add(-2, 0, -2);
            player.teleport(safeLocation);
        }

        MessageUtil.sendMessage(
                player,
                MessageProvider.Errors.worldIsBlocked(),
                true,
                List.of("%world%"),
                List.of(newWorld)
        );
    }
}
