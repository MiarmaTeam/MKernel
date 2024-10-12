package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.Constants;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

public class GmaCommand {
    public static void register() {
        CommandWrapper gmaCmd = CommandProvider.getGmaCommand();
        new CommandAPICommand(gmaCmd.getName())
            .withOptionalArguments(
                PLAYERS_OPT_ARG.withPermission(
                    gmaCmd.getPermission().others()
                )
            )
            .withShortDescription(gmaCmd.getDescription())
            .withFullDescription(gmaCmd.getDescription())
            .withUsage(gmaCmd.getUsage())
            .executesPlayer((sender,args) -> {
                if(args.rawArgs().length == 0) {
                    sender.setGameMode(GameMode.ADVENTURE);
                    Utils.sendMessage(gmaCmd.getMessages()[0], sender, true);
                } else {
                    Bukkit.getPlayer(args.getRaw(0)).setGameMode(GameMode.ADVENTURE);
                    Utils.sendMessage(gmaCmd.getMessages()[1], sender, true,
                            true, List.of("%player%"), List.of(args.getRaw(0)));
                }
            })
            .register();
    }
}
