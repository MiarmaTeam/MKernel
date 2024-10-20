package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

public class GmcCommand {
    public static void register() {
        CommandWrapper gmcCmd = CommandProvider.getGmcCommand();
        new CommandAPICommand(gmcCmd.getName())
            .withOptionalArguments(
                PLAYERS_OPT_ARG.withPermission(
                    gmcCmd.getPermission().others()
                )
            )
            .withShortDescription(gmcCmd.getDescription())
            .withFullDescription(gmcCmd.getDescription())
            .withUsage(gmcCmd.getUsage())
            .executesPlayer((sender,args) -> {
                if(args.rawArgs().length == 0) {
                    sender.setGameMode(GameMode.CREATIVE);
                    MessageUtil.sendMessage(sender, gmcCmd.getMessages()[0], true);
                } else {
                    Bukkit.getPlayer(args.getRaw(0)).setGameMode(GameMode.CREATIVE);
                    MessageUtil.sendMessage(sender, gmcCmd.getMessages()[1], true,
                                                List.of("%player%"), List.of(args.getRaw(0)));
                }
            })
            .register();
    }
}
