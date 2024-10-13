package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYER_ARG;
import static dev.gallardo.miarmacore.util.Constants.FROZEN_KEY;

public class FreezeCommand {
    public static void register() {
        CommandWrapper freezeCmd = CommandProvider.getFreezeCommand();
        new CommandAPICommand(freezeCmd.getName())
            .withArguments(PLAYER_ARG)
            .withFullDescription(freezeCmd.getDescription())
            .withShortDescription(freezeCmd.getDescription())
            .withUsage(freezeCmd.getUsage())
            .withPermission(freezeCmd.getPermission().base())
            .executesPlayer((sender, args) -> {
                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null) {
                    MessageUtils.sendMessage(sender, MessageProvider.Errors.playerNotFound(), true);
                }

                if(args.count() > 1) {
                    MessageUtils.sendMessage(sender, MessageProvider.Errors.tooManyArguments(), true);
                }

                PersistentDataContainer data = target.getPersistentDataContainer();
                boolean isFrozen = Boolean.TRUE.equals(data.get(FROZEN_KEY, PersistentDataType.BOOLEAN));
                data.set(FROZEN_KEY, PersistentDataType.BOOLEAN, !isFrozen);

                if(isFrozen) {
                    unsetFrozen(target);
                    MessageUtils.sendMessage(sender, freezeCmd.getMessages()[1], true,
                            List.of("%player%"), List.of(target.getName()));
                    MessageUtils.sendMessage(target, freezeCmd.getMessages()[3], true,
                            List.of("%sender%"), List.of(sender.getName()));
                } else {
                    setFrozen(target);
                    MessageUtils.sendMessage(sender, freezeCmd.getMessages()[0], true,
                            List.of("%player%"), List.of(target.getName()));
                    MessageUtils.sendMessage(target, freezeCmd.getMessages()[2], true,
                            List.of("%sender%"), List.of(sender.getName()));
                }

            })
            .register();
    }

    private static void setFrozen(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(FROZEN_KEY, PersistentDataType.BOOLEAN, true);
    }

    private static void unsetFrozen(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(FROZEN_KEY, PersistentDataType.BOOLEAN, false);
    }
}
