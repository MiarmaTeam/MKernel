package dev.gallardo.miarmacore.config.providers;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.config.WorldWrapper;
import dev.gallardo.miarmacore.util.Utils;
import org.bukkit.World;

public class ConfigProvider {
    public static class Permissions {
        public static String getChatFormatPermission() {
            return MiarmaCore.CONFIG.getString("config.permissions.chatFormat");
        }
        public static String getAdminChatPermission() {
            return MiarmaCore.CONFIG.getString("config.permissions.adminChat");
        }
        public static String getMentionsPermission() {
            return MiarmaCore.CONFIG.getString("config.permissions.mentions");
        }
    }

    public static class Values {
        public static int getRottenFleshCookTime() {
            return MiarmaCore.CONFIG.getInt("config.values.rottenFleshCookTime");
        }
        public static int getZombificationPotionTime() {
            return MiarmaCore.CONFIG.getInt("config.values.zombificationPotionTime");
        }
        public static int getSpawnerBreakerProbability() {
            return MiarmaCore.CONFIG.getInt("config.values.spawnerBreakerProbability");
        }
        public static int getRecInvRequiredLevel() {
            return MiarmaCore.CONFIG.getInt("config.values.recInvRequiredLevel");
        }
        public static float getXpLossOnDeath() {
            return MiarmaCore.CONFIG.getConfig().getFloat("config.values.xpLossOnDeath");
        }
        public static long getTpCooldown() {
            return Utils.cooldownToMillis(MiarmaCore.CONFIG.getString("config.values.tpCooldown"));
        }
        public static int getMaxWarps() {
            return MiarmaCore.CONFIG.getInt("config.values.maxWarps");
        }
        public static int getRecInvPlayerRadius() {
            return MiarmaCore.CONFIG.getInt("config.values.recInvPlayerRadius");
        }
        public static int getRecInvSpawnDistance() {
            return MiarmaCore.CONFIG.getInt("config.values.recInvSpawnDistance");
        }
    }

    public static class Modules {
        public static boolean isJoinTitleEnabled() {
            return MiarmaCore.CONFIG.getBoolean("config.modules.joinTitle");
        }
        public static boolean isLeaveTitleEnabled() {
            return MiarmaCore.CONFIG.getBoolean("config.modules.leaveTitle");
        }
        public static boolean isDeathTitleEnabled() {
            return MiarmaCore.CONFIG.getBoolean("config.modules.deathTitle");
        }
        public static boolean isHarvestOnRightClickEnabled() {
            return MiarmaCore.CONFIG.getBoolean("config.modules.harvestOnRightClick");
        }
        public static boolean isAutoItemRefillEnabled() {
            return MiarmaCore.CONFIG.getBoolean("config.modules.autoItemRefill");
        }
        public static boolean isAdminChatEnabled() {
            return MiarmaCore.CONFIG.getBoolean("config.modules.adminChat");
        }
        public static boolean isChatFormatEnabled() {
            return MiarmaCore.CONFIG.getBoolean("config.modules.chatFormat");
        }
        public static boolean isMentionsEnabled() {
            return MiarmaCore.CONFIG.getBoolean("config.modules.mentions");
        }
        public static boolean isBookColorsEnabled() {
            return MiarmaCore.CONFIG.getBoolean("config.modules.bookColors");
        }
        public static boolean isSpawnAtLobbyEnabled() {
            return MiarmaCore.CONFIG.getBoolean("config.modules.spawnAtLobby");
        }
        public static boolean isRecoverInventoryEnabled() {
            return MiarmaCore.CONFIG.getBoolean("config.modules.recoverInventory");
        }
    }

    public static class Worlds {
        public static WorldWrapper getLobby() {
            String name = MiarmaCore.CONFIG.getString("config.worlds.lobby.name");
            double x = MiarmaCore.CONFIG.getConfig().getDouble("config.worlds.lobby.x");
            double y = MiarmaCore.CONFIG.getConfig().getDouble("config.worlds.lobby.y");
            double z = MiarmaCore.CONFIG.getConfig().getDouble("config.worlds.lobby.z");
            int yaw = MiarmaCore.CONFIG.getConfig().getInt("config.worlds.lobby.yaw");
            int pitch = MiarmaCore.CONFIG.getConfig().getInt("config.worlds.lobby.pitch");
            return new WorldWrapper(name, x, y, z, yaw, pitch);
        }
    }
}