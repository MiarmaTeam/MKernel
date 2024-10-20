package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static dev.gallardo.miarmacore.util.Constants.SPY_KEY;

public class SpyCommand {
    public static void register() {
        CommandWrapper spyCmd = CommandProvider.getSpyCommand();
        new CommandAPICommand(spyCmd.getName())
            .withFullDescription(spyCmd.getDescription())
            .withShortDescription(spyCmd.getDescription())
            .withPermission(spyCmd.getPermission().base())
            .executesPlayer((sender, args) -> {
                PersistentDataContainer data = sender.getPersistentDataContainer();
                boolean canSpy = Boolean.TRUE.equals(data.get(SPY_KEY, PersistentDataType.BOOLEAN));

                if (canSpy) {
                    unsetSpy(sender);
                    MessageUtil.sendMessage(sender, spyCmd.getMessages()[1], true);
                } else {
                    setSpy(sender);
                    MessageUtil.sendMessage(sender, spyCmd.getMessages()[0], true);
                }
            })
            .register();
    }

    private static void setSpy(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(SPY_KEY, PersistentDataType.BOOLEAN, true);
    }

    private static void unsetSpy(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(SPY_KEY, PersistentDataType.BOOLEAN, false);
    }
}
