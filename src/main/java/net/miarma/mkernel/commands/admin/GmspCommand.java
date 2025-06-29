package net.miarma.mkernel.commands.admin;

import net.miarma.mkernel.config.CommandWrapper;
import net.miarma.mkernel.config.providers.CommandProvider;
import net.miarma.mkernel.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;

import static net.miarma.mkernel.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

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
                    MessageUtil.sendMessage(sender, gmspCmd.getMessages()[0], true);
                } else {
                    Bukkit.getPlayer(args.getRaw(0)).setGameMode(GameMode.SPECTATOR);
                    MessageUtil.sendMessage(sender, gmspCmd.getMessages()[1], true,
                                                List.of("%player%"), List.of(args.getRaw(0)));
                }
            })
            .register();
    }
}
