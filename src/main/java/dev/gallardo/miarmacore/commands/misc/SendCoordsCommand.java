package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import dev.gallardo.miarmacore.MiarmaCore;

import java.util.List;

import static dev.gallardo.miarmacore.util.Constants.*;

public class SendCoordsCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.sendcoords.name"))
            .withArguments(PLAYER_ARG)
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.sendcoords.description"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.sendcoords.permission"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.sendcoords.description"))
            .executesPlayer((sender, args) -> {
                if (args.count() > 1) {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.tooManyArguments"), sender, true);
                }

                Player player = Bukkit.getPlayer(args.getRaw(0));
                Location loc = sender.getLocation();
                List<String> coords = List.of(String.valueOf(loc.getBlockX()),String.valueOf(loc.getBlockY()),String.valueOf(loc.getBlockZ()));

                Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.sendcoords.messages.sent"), sender, true,
                                    true, List.of("%target%"), List.of(player.getName()));
                Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.sendcoords.messages.coordsMsg"), player, true,
                        true, List.of("%sender%", "%x%", "%y%", "%z%"), List.of(sender.getName(), coords.get(0), coords.get(1), coords.get(2)));
            })
            .register();
    }
}
