package dev.gallardo.miarmacore.tasks;

import dev.gallardo.miarmacore.MiarmaCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class LocationTracker {
    private static Map<Player, Location> LOCATIONS = new HashMap<>();

    public static void startLocationTrackingTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                	if(player.getWorld().getName().equals(Bukkit.getServer().getWorlds().get(0).getName())) {
                		Location currentLocation = player.getLocation();
                		LOCATIONS.put(player, currentLocation);
                	}
                }
            }
        }.runTaskTimer(MiarmaCore.PLUGIN, 0, 20);
    }

    public static Location getPlayerLocation(Player player) {
        return LOCATIONS.get(player);
    }
}
