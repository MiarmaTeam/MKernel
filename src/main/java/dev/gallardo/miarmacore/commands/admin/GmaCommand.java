package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Constants;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;

public class GmaCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.gma.name"))
            .withOptionalArguments(
                    Constants.PLAYERS_OPT_ARG.withPermission(
                            MiarmaCore.CONFIG.getString("commands.gma.permissions.others")
                    )
            )
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.gma.description"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.gma.description"))
            .withUsage(MiarmaCore.CONFIG.getString("commands.gma.usage"))
            .executesPlayer((sender,args) -> {
                if(args.rawArgs().length == 0) {
                    sender.setGameMode(GameMode.ADVENTURE);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.gma.messages.self"), sender, true);
                } else {
                    Bukkit.getPlayer(args.getRaw(0)).setGameMode(GameMode.ADVENTURE);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.gma.messages.others"), sender, true,
                            true, List.of("%player%"), List.of(args.getRaw(0)));
                }
            })
            .register();
    }
}
