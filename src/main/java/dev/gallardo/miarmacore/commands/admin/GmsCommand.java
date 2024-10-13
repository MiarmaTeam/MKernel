package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

public class GmsCommand {
    public static void register() {
        CommandWrapper gmsCmd = CommandProvider.getGmsCommand();
        new CommandAPICommand(gmsCmd.getName())
            .withOptionalArguments(
                PLAYERS_OPT_ARG.withPermission(
                    gmsCmd.getPermission().others()
                )
            )
            .withShortDescription(gmsCmd.getDescription())
            .withFullDescription(gmsCmd.getDescription())
            .withUsage(gmsCmd.getUsage())
            .executesPlayer((sender,args) -> {
                if(args.rawArgs().length == 0) {
                    sender.setGameMode(GameMode.SURVIVAL);
                    MessageUtils.sendMessage(sender, gmsCmd.getMessages()[0], true);
                } else {
                    Bukkit.getPlayer(args.getRaw(0)).setGameMode(GameMode.SURVIVAL);
                    MessageUtils.sendMessage(sender, gmsCmd.getMessages()[1], true,
                                                List.of("%player%"), List.of(args.getRaw(0)));
                }
            })
            .register();
    }
}
