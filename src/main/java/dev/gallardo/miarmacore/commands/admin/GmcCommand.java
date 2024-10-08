package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Constants;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;

public class GmcCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.gmc.name"))
            .withOptionalArguments(
                Constants.PLAYERS_OPT_ARG.withPermission(
                        MiarmaCore.CONFIG.getString("commands.gmc.permissions.others")
                )
            )
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.gmc.description"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.gmc.description"))
            .withUsage(MiarmaCore.CONFIG.getString("commands.gmc.usage"))
            .executesPlayer((sender,args) -> {
                if(args.rawArgs().length == 0) {
                    sender.setGameMode(GameMode.CREATIVE);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.gmc.messages.self"), sender, true);
                } else {
                    Bukkit.getPlayer(args.getRaw(0)).setGameMode(GameMode.CREATIVE);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.gmc.messages.others"), sender, true,
                            true, List.of("%player%"), List.of(args.getRaw(0)));
                }
            })
            .register();
    }
}
