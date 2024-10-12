package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
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
                if (data.has(SPY_KEY)) {
                    data.remove(SPY_KEY);
                    Utils.sendMessage(spyCmd.getMessages()[1], sender, true);
                } else {
                    data.set(SPY_KEY, PersistentDataType.BOOLEAN, true);
                    Utils.sendMessage(spyCmd.getMessages()[0], sender, true);
                }
            })
            .register();
    }
}
