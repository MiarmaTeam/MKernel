package net.miarma.mkernel.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;

public class VersionUtil {

    public static boolean isAtLeast(String version) {
        String[] current = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        String[] target = version.split("\\.");

        for (int i = 0; i < target.length; i++) {
            int currentPart = (i < current.length) ? parse(current[i]) : 0;
            int targetPart = parse(target[i]);
            if (currentPart < targetPart) return false;
            if (currentPart > targetPart) return true;
        }
        return true;
    }

    public static Material getSafeMaterial(String name) {
        try {
            return Material.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean isSupported(String materialName) {
        return getSafeMaterial(materialName) != null;
    }

    private static int parse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
