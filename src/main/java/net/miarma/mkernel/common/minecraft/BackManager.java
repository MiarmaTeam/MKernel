package net.miarma.mkernel.common.minecraft;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BackManager {
    private static final Map<UUID, Location> lastLocations = new HashMap<>();

    public static void setLastLocation(Player player, Location location) {
        lastLocations.put(player.getUniqueId(), location);
    }

    public static Location getLastLocation(Player player) {
        return lastLocations.get(player.getUniqueId());
    }

    public static Location removeLastLocation(Player player) {
        return lastLocations.remove(player.getUniqueId());
    }

    public static boolean hasBackLocation(Player player) {
        return lastLocations.containsKey(player.getUniqueId());
    }
}
