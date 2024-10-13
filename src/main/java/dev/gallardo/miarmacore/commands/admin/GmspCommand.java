package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

public class GmspCommand {
    public static void register() {
        CommandWrapper gmspCmd = CommandProvider.getGmspCommand();
        new CommandAPICommand(gmspCmd.getName())
            .withOptionalArguments(
                PLAYERS_OPT_ARG.withPermission(
                    gmspCmd.getPermission().others()
                )
            )
            .withShortDescription(gmspCmd.getDescription())
            .withFullDescription(gmspCmd.getDescription())
            .withUsage(gmspCmd.getUsage())
            .executesPlayer((sender,args) -> {
                if(args.rawArgs().length == 0) {
                    sender.setGameMode(GameMode.SPECTATOR);
                    MessageUtils.sendMessage(gmspCmd.getMessages()[0], sender, true);
                } else {
                    Bukkit.getPlayer(args.getRaw(0)).setGameMode(GameMode.SPECTATOR);
                    MessageUtils.sendMessage(gmspCmd.getMessages()[1], sender, true,
                            true, List.of("%player%"), List.of(args.getRaw(0)));
                }
            })
            .register();
    }
}
