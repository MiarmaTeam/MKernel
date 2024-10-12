package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import dev.gallardo.miarmacore.MiarmaCore;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYER_ARG;
import static dev.gallardo.miarmacore.util.Constants.*;

public class SendCoordsCommand {
    public static void register() {
        CommandWrapper sendCoordsCmd = CommandProvider.getSendCoordsCommand();
        new CommandAPICommand(sendCoordsCmd.getName())
            .withArguments(PLAYER_ARG)
            .withFullDescription(sendCoordsCmd.getDescription())
            .withPermission(sendCoordsCmd.getPermission().base())
            .withShortDescription(sendCoordsCmd.getDescription())
            .executesPlayer((sender, args) -> {
                if (args.count() > 1) {
                    Utils.sendMessage(MessageProvider.Errors.tooManyArguments(), sender, true);
                }

                Player player = Bukkit.getPlayer(args.getRaw(0));
                Location loc = sender.getLocation();
                List<String> coords = List.of(String.valueOf(loc.getBlockX()),String.valueOf(loc.getBlockY()),String.valueOf(loc.getBlockZ()));

                Utils.sendMessage(sendCoordsCmd.getMessages()[0], sender, true,
                                    true, List.of("%target%"), List.of(player.getName()));
                Utils.sendMessage(sendCoordsCmd.getMessages()[1], player, true,
                        true, List.of("%sender%", "%x%", "%y%", "%z%"), List.of(sender.getName(), coords.get(0), coords.get(1), coords.get(2)));
            })
            .register();
    }
}
