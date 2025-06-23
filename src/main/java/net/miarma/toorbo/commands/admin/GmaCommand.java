package net.miarma.toorbo.commands.admin;

import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;

import static net.miarma.toorbo.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

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
                    MessageUtil.sendMessage(sender, gmaCmd.getMessages()[0], true);
                } else {
                    Bukkit.getPlayer(args.getRaw(0)).setGameMode(GameMode.ADVENTURE);
                    MessageUtil.sendMessage(sender, gmaCmd.getMessages()[1], true,
                                                List.of("%player%"), List.of(args.getRaw(0)));
                }
            })
            .register();
    }
}
