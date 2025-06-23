package net.miarma.toorbo.commands.admin;

import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;

import static net.miarma.toorbo.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

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
                    MessageUtil.sendMessage(sender, gmsCmd.getMessages()[0], true);
                } else {
                    Bukkit.getPlayer(args.getRaw(0)).setGameMode(GameMode.SURVIVAL);
                    MessageUtil.sendMessage(sender, gmsCmd.getMessages()[1], true,
                                                List.of("%player%"), List.of(args.getRaw(0)));
                }
            })
            .register();
    }
}
