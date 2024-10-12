package dev.gallardo.miarmacore.config.providers;

import dev.gallardo.miarmacore.MiarmaCore;

public class MessageProvider {
    public static String getPrefix() {
        return MiarmaCore.CONFIG.getString("language.prefix");
    }
    public static String getAdminPrefix() {
        return MiarmaCore.CONFIG.getString("language.adminPrefix");
    }

    public static class Inventories {
        public static String getGlobalChestTitle() {
            return MiarmaCore.CONFIG.getString("language.inventories.globalChest.title");
        }
        public static String getDisposalTitle() {
            return MiarmaCore.CONFIG.getString("language.inventories.disposal.title");
        }
        public static String getConfigMenuTitle() {
            return MiarmaCore.CONFIG.getString("language.inventories.configMenu.title");
        }
        public static String getConfigMenuValueName() {
            return MiarmaCore.CONFIG.getString("language.inventories.configMenu.valueName");
        }
        public static String getConfigMenuValueLore() {
            return MiarmaCore.CONFIG.getString("language.inventories.configMenu.valueLore");
        }
    }

    public static class Titles {
        public static String getTitleFormat() {
            return MiarmaCore.CONFIG.getString("language.titles.titleFormat");
        }
        public static String getJoinSubtitle() {
            return MiarmaCore.CONFIG.getString("language.titles.subtitles.join");
        }
        public static String getLeaveSubtitle() {
            return MiarmaCore.CONFIG.getString("language.titles.subtitles.leave");
        }
        public static String getDeathSubtitle() {
            return MiarmaCore.CONFIG.getString("language.titles.subtitles.death");
        }
    }

    public static class Events {
        public static String getOnMentionFormat() {
            return MiarmaCore.CONFIG.getString("language.events.onMention.format");
        }
        public static String getOnMentionMessage() {
            return MiarmaCore.CONFIG.getString("language.events.onMention.youWereMentioned");
        }
        public static String getOnDeathLostLevelsItems() {
            return MiarmaCore.CONFIG.getString("language.events.onDeath.lostLevelsItems");
        }
        public static String getOnDeathItemsNotRecovered() {
            return MiarmaCore.CONFIG.getString("language.events.onDeath.itemsNotRecovered");
        }
    }

    public static class Items {
        public static String getScissorsName() {
            return MiarmaCore.CONFIG.getString("language.items.scissors.name");
        }
        public static String getScissorsLore() {
            return MiarmaCore.CONFIG.getString("language.items.scissors.lore");
        }
        public static String getAdminStickName() {
            return MiarmaCore.CONFIG.getString("language.items.adminStick.name");
        }
        public static String getAdminStickLore() {
            return MiarmaCore.CONFIG.getString("language.items.adminStick.lore");
        }
        public static String getSpawnerBreakerName() {
            return MiarmaCore.CONFIG.getString("language.items.spawnerBreaker.name");
        }
        public static String getSpawnerBreakerLore() {
            return MiarmaCore.CONFIG.getString("language.items.spawnerBreaker.lore");
        }
        public static String getZombificationPotionName() {
            return MiarmaCore.CONFIG.getString("language.items.zombificationPotion.name");
        }
        public static String getZombificationPotionLore() {
            return MiarmaCore.CONFIG.getString("language.items.zombificationPotion.lore");
        }
        public static String getBolsitaName() {
            return MiarmaCore.CONFIG.getString("language.items.bolsita.name");
        }
        public static String getBolsitaLore() {
            return MiarmaCore.CONFIG.getString("language.items.bolsita.lore");
        }
    }

    public static class Errors {
        public static String failedCreatingLangs() {
            return MiarmaCore.CONFIG.getString("language.errors.langs");
        }
        public static String notConsoleCommand() {
            return MiarmaCore.CONFIG.getString("language.errors.notConsoleCommand");
        }
        public static String onlyPlayerCommand() {
            return MiarmaCore.CONFIG.getString("language.errors.onlyPlayerCommand");
        }
        public static String playerNotFound() {
            return MiarmaCore.CONFIG.getString("language.errors.playerNotFound");
        }
        public static String cantTeleportToYourself() {
            return MiarmaCore.CONFIG.getString("language.errors.cantTeleportToYourself");
        }
        public static String requestAlreadySent() {
            return MiarmaCore.CONFIG.getString("language.errors.requestAlreadySent");
        }
        public static String noRequestFound() {
            return MiarmaCore.CONFIG.getString("language.errors.noRequestFound");
        }
        public static String noPermission() {
            return MiarmaCore.CONFIG.getString("language.errors.noPermission");
        }
        public static String tooManyArguments() {
            return MiarmaCore.CONFIG.getString("language.errors.tooManyArguments");
        }
        public static String playerRequired() {
            return MiarmaCore.CONFIG.getString("language.errors.playerRequired");
        }
        public static String invalidArgument() {
            return MiarmaCore.CONFIG.getString("language.errors.invalidArgument");
        }
        public static String temporarilyDisabled() {
            return MiarmaCore.CONFIG.getString("language.errors.temporarilyDisabled");
        }
        public static String itemNotFound() {
            return MiarmaCore.CONFIG.getString("language.errors.itemNotFound");
        }
        public static String lobbyDoesNotExist() {
            return MiarmaCore.CONFIG.getString("language.errors.lobbyDoesNotExist");
        }
        public static String worldIsBlocked() {
            return MiarmaCore.CONFIG.getString("language.errors.worldIsBlocked");
        }
        public static String notEnoughLevels() {
            return MiarmaCore.CONFIG.getString("language.errors.notEnoughLevels");
        }
        public static String cooldownHasNotExpired() {
            return MiarmaCore.CONFIG.getString("language.errors.cooldownHasNotExpired");
        }
        public static String maxWarpsReached() {
            return MiarmaCore.CONFIG.getString("language.errors.maxWarpsReached");
        }
    }
}
