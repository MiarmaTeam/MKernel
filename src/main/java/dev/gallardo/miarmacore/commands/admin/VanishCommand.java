package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.ConfigProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;

import static dev.gallardo.miarmacore.util.Constants.VANISH_KEY;

public class VanishCommand {
    public static void register() {
        CommandWrapper vanishCmd = CommandProvider.getVanishCommand();
        new CommandAPICommand(vanishCmd.getName())
            .withAliases("v")
            .withFullDescription(vanishCmd.getDescription())
            .withShortDescription(vanishCmd.getDescription())
            .withPermission(vanishCmd.getPermission().base())
            .executesPlayer((sender,args) -> {
                PersistentDataContainer data = sender.getPersistentDataContainer();
                boolean isVanished = Boolean.TRUE.equals(data.get(VANISH_KEY, PersistentDataType.BOOLEAN));
                data.set(VANISH_KEY, PersistentDataType.BOOLEAN, !isVanished);

                if(isVanished) {
                    unsetVanish(sender);
                    MessageUtils.sendMessage(sender,
                            vanishCmd.getMessages()[1], true);
                } else {
                    setVanish(sender);
                    MessageUtils.sendMessage(sender,
                            vanishCmd.getMessages()[0], true);
                }
            })
            .register();
    }

    private static void setVanish(Player player) {
        player.setInvisible(true);
        player.setCanPickupItems(false);
    }

    private static void unsetVanish(Player player) {
        player.setInvisible(false);
        player.setCanPickupItems(true);
    }
}
