package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Constants;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;

public class GmsCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.gms.name"))
            .withOptionalArguments(
                    Constants.PLAYERS_OPT_ARG.withPermission(
                            MiarmaCore.CONFIG.getString("commands.gms.permissions.others")
                    )
            )
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.gms.description"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.gms.description"))
            .withUsage(MiarmaCore.CONFIG.getString("commands.gms.usage"))
            .executesPlayer((sender,args) -> {
                if(args.rawArgs().length == 0) {
                    sender.setGameMode(GameMode.SURVIVAL);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.gms.messages.self"), sender, true);
                } else {
                    Bukkit.getPlayer(args.getRaw(0)).setGameMode(GameMode.SURVIVAL);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.gms.messages.others"), sender, true,
                            true, List.of("%player%"), List.of(args.getRaw(0)));
                }
            })
            .register();
    }
}
