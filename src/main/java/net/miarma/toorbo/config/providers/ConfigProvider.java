package net.miarma.toorbo.config.providers;

import net.miarma.toorbo.Toorbo;
import net.miarma.toorbo.config.WorldWrapper;
import net.miarma.toorbo.util.ConversionUtil;

public class ConfigProvider {
    public static class Permissions {
        public static String getChatFormatPermission() {
            return Toorbo.CONFIG.getString("config.permissions.chatFormat");
        }
        public static String getAdminChatPermission() {
            return Toorbo.CONFIG.getString("config.permissions.adminChat");
        }
        public static String getMentionsPermission() {
            return Toorbo.CONFIG.getString("config.permissions.mentions");
        }
    }

    public static class Values {
        public static int getRottenFleshCookTime() {
            return Toorbo.CONFIG.getInt("config.values.rottenFleshCookTime");
        }
        public static int getZombificationPotionTime() {
            return Toorbo.CONFIG.getInt("config.values.zombificationPotionTime");
        }
        public static int getSpawnerBreakerProbability() {
            return Toorbo.CONFIG.getInt("config.values.spawnerBreakerProbability");
        }
        public static int getRecInvRequiredLevel() {
            return Toorbo.CONFIG.getInt("config.values.recInvRequiredLevel");
        }
        public static float getXpLossOnDeath() {
            return Toorbo.CONFIG.getConfig().getFloat("config.values.xpLossOnDeath");
        }
        public static long getTpCooldown() {
            return ConversionUtil.cooldownToMillis(Toorbo.CONFIG.getString("config.values.tpCooldown"));
        }
        public static int getMaxWarps() {
            return Toorbo.CONFIG.getInt("config.values.maxWarps");
        }
        public static int getRecInvPlayerRadius() {
            return Toorbo.CONFIG.getInt("config.values.recInvPlayerRadius");
        }
        public static int getRecInvSpawnDistance() {
            return Toorbo.CONFIG.getInt("config.values.recInvSpawnDistance");
        }
    }

    public static class Modules {
        public static boolean isJoinTitleEnabled() {
            return Toorbo.CONFIG.getBoolean("config.modules.joinTitle");
        }
        public static boolean isLeaveTitleEnabled() {
            return Toorbo.CONFIG.getBoolean("config.modules.leaveTitle");
        }
        public static boolean isDeathTitleEnabled() {
            return Toorbo.CONFIG.getBoolean("config.modules.deathTitle");
        }
        public static boolean isHarvestOnRightClickEnabled() {
            return Toorbo.CONFIG.getBoolean("config.modules.harvestOnRightClick");
        }
        public static boolean isAutoItemRefillEnabled() {
            return Toorbo.CONFIG.getBoolean("config.modules.autoItemRefill");
        }
        public static boolean isAdminChatEnabled() {
            return Toorbo.CONFIG.getBoolean("config.modules.adminChat");
        }
        public static boolean isChatFormatEnabled() {
            return Toorbo.CONFIG.getBoolean("config.modules.chatFormat");
        }
        public static boolean isMentionsEnabled() {
            return Toorbo.CONFIG.getBoolean("config.modules.mentions");
        }
        public static boolean isBookColorsEnabled() {
            return Toorbo.CONFIG.getBoolean("config.modules.bookColors");
        }
        public static boolean isSpawnAtLobbyEnabled() {
            return Toorbo.CONFIG.getBoolean("config.modules.spawnAtLobby");
        }
        public static boolean isRecoverInventoryEnabled() {
            return Toorbo.CONFIG.getBoolean("config.modules.recoverInventory");
        }
    }

    public static class Worlds {
        public static WorldWrapper getLobby() {
            String name = Toorbo.CONFIG.getString("config.worlds.lobby.name");
            double x = Toorbo.CONFIG.getConfig().getDouble("config.worlds.lobby.x");
            double y = Toorbo.CONFIG.getConfig().getDouble("config.worlds.lobby.y");
            double z = Toorbo.CONFIG.getConfig().getDouble("config.worlds.lobby.z");
            int yaw = Toorbo.CONFIG.getConfig().getInt("config.worlds.lobby.yaw");
            int pitch = Toorbo.CONFIG.getConfig().getInt("config.worlds.lobby.pitch");
            return new WorldWrapper(name, x, y, z, yaw, pitch);
        }
    }
}