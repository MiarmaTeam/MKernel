package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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
                data.set(VANISH_KEY, PersistentDataType.BOOLEAN, !isVanished(sender));

                if(isVanished(sender)) {
                    setVanish(sender);
                    MessageUtils.sendMessage(vanishCmd.getMessages()[0],
                            sender, true);
                } else {
                    unsetVanish(sender);
                    MessageUtils.sendMessage(vanishCmd.getMessages()[1],
                            sender, true);
                }
            })
            .register();
    }

    private static boolean isVanished(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        return Boolean.TRUE.equals(data.get(VANISH_KEY, PersistentDataType.BOOLEAN));
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
