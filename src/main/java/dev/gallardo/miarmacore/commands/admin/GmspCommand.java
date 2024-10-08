package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Constants;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;

public class GmspCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.gmsp.name"))
            .withOptionalArguments(
                    Constants.PLAYERS_OPT_ARG.withPermission(
                            MiarmaCore.CONFIG.getString("commands.gmsp.permissions.others")
                    )
            )
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.gmsp.description"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.gmsp.description"))
            .withUsage(MiarmaCore.CONFIG.getString("commands.gmsp.usage"))
            .executesPlayer((sender,args) -> {
                if(args.rawArgs().length == 0) {
                    sender.setGameMode(GameMode.SPECTATOR);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.gmsp.messages.self"), sender, true);
                } else {
                    Bukkit.getPlayer(args.getRaw(0)).setGameMode(GameMode.SPECTATOR);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.gmsp.messages.others"), sender, true,
                            true, List.of("%player%"), List.of(args.getRaw(0)));
                }
            })
            .register();
    }
}
