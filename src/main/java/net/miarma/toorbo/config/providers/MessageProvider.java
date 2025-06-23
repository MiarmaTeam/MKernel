package net.miarma.toorbo.config.providers;

import net.miarma.toorbo.Toorbo;

public class MessageProvider {
    public static String getPrefix() {
        return Toorbo.CONFIG.getString("language.prefix");
    }
    public static String getAdminPrefix() {
        return Toorbo.CONFIG.getString("language.adminPrefix");
    }

    public static class Inventories {
        public static String getGlobalChestTitle() {
            return Toorbo.CONFIG.getString("language.inventories.globalChest.title");
        }
        public static String getDisposalTitle() {
            return Toorbo.CONFIG.getString("language.inventories.disposal.title");
        }
        public static String getConfigMenuTitle() {
            return Toorbo.CONFIG.getString("language.inventories.configMenu.title");
        }
        public static String getConfigMenuValueName() {
            return Toorbo.CONFIG.getString("language.inventories.configMenu.valueName");
        }
        public static String getConfigMenuValueLore() {
            return Toorbo.CONFIG.getString("language.inventories.configMenu.valueLore");
        }
    }

    public static class Titles {
        public static String getTitleFormat() {
            return Toorbo.CONFIG.getString("language.titles.titleFormat");
        }
        public static String getJoinSubtitle() {
            return Toorbo.CONFIG.getString("language.titles.subtitles.join");
        }
        public static String getLeaveSubtitle() {
            return Toorbo.CONFIG.getString("language.titles.subtitles.leave");
        }
        public static String getDeathSubtitle() {
            return Toorbo.CONFIG.getString("language.titles.subtitles.death");
        }
    }

    public static class Events {
        public static String getOnMentionFormat() {
            return Toorbo.CONFIG.getString("language.events.onMention.format");
        }
        public static String getOnMentionMessage() {
            return Toorbo.CONFIG.getString("language.events.onMention.youWereMentioned");
        }
        public static String getOnDeathLostLevelsItems() {
            return Toorbo.CONFIG.getString("language.events.onDeath.lostLevelsItems");
        }
        public static String getOnDeathItemsNotRecovered() {
            return Toorbo.CONFIG.getString("language.events.onDeath.itemsNotRecovered");
        }
        public static String getOnCommandSpyMessage() {
            return Toorbo.CONFIG.getString("language.events.onCommand.spyMessage");
        }
    }

    public static class Items {
        public static String getScissorsName() {
            return Toorbo.CONFIG.getString("language.items.scissors.name");
        }
        public static String getScissorsLore() {
            return Toorbo.CONFIG.getString("language.items.scissors.lore");
        }
        public static String getAdminStickName() {
            return Toorbo.CONFIG.getString("language.items.adminStick.name");
        }
        public static String getAdminStickLore() {
            return Toorbo.CONFIG.getString("language.items.adminStick.lore");
        }
        public static String getSpawnerBreakerName() {
            return Toorbo.CONFIG.getString("language.items.spawnerBreaker.name");
        }
        public static String getSpawnerBreakerLore() {
            return Toorbo.CONFIG.getString("language.items.spawnerBreaker.lore");
        }
        public static String getZombificationPotionName() {
            return Toorbo.CONFIG.getString("language.items.zombificationPotion.name");
        }
        public static String getZombificationPotionLore() {
            return Toorbo.CONFIG.getString("language.items.zombificationPotion.lore");
        }
        public static String getBolsitaName() {
            return Toorbo.CONFIG.getString("language.items.bolsita.name");
        }
        public static String getBolsitaLore() {
            return Toorbo.CONFIG.getString("language.items.bolsita.lore");
        }
    }

    public static class Errors {
        public static String failedCreatingLangs() {
            return Toorbo.CONFIG.getString("language.errors.langs");
        }
        public static String notConsoleCommand() {
            return Toorbo.CONFIG.getString("language.errors.notConsoleCommand");
        }
        public static String onlyPlayerCommand() {
            return Toorbo.CONFIG.getString("language.errors.onlyPlayerCommand");
        }
        public static String playerNotFound() {
            return Toorbo.CONFIG.getString("language.errors.playerNotFound");
        }
        public static String cantTeleportToYourself() {
            return Toorbo.CONFIG.getString("language.errors.cantTeleportToYourself");
        }
        public static String requestAlreadySent() {
            return Toorbo.CONFIG.getString("language.errors.requestAlreadySent");
        }
        public static String noRequestFound() {
            return Toorbo.CONFIG.getString("language.errors.noRequestFound");
        }
        public static String noPermission() {
            return Toorbo.CONFIG.getString("language.errors.noPermission");
        }
        public static String tooManyArguments() {
            return Toorbo.CONFIG.getString("language.errors.tooManyArguments");
        }
        public static String playerRequired() {
            return Toorbo.CONFIG.getString("language.errors.playerRequired");
        }
        public static String invalidArgument() {
            return Toorbo.CONFIG.getString("language.errors.invalidArgument");
        }
        public static String temporarilyDisabled() {
            return Toorbo.CONFIG.getString("language.errors.temporarilyDisabled");
        }
        public static String itemNotFound() {
            return Toorbo.CONFIG.getString("language.errors.itemNotFound");
        }
        public static String lobbyDoesNotExist() {
            return Toorbo.CONFIG.getString("language.errors.lobbyDoesNotExist");
        }
        public static String worldIsBlocked() {
            return Toorbo.CONFIG.getString("language.errors.worldIsBlocked");
        }
        public static String notEnoughLevels() {
            return Toorbo.CONFIG.getString("language.errors.notEnoughLevels");
        }
        public static String cooldownHasNotExpired() {
            return Toorbo.CONFIG.getString("language.errors.cooldownHasNotExpired");
        }
        public static String maxWarpsReached() {
            return Toorbo.CONFIG.getString("language.errors.maxWarpsReached");
        }
    }
}
