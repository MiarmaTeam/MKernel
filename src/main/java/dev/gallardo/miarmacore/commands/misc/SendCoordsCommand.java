package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYER_ARG;

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
                    MessageUtils.sendMessage(sender, MessageProvider.Errors.tooManyArguments(), true);
                }

                Player player = Bukkit.getPlayer(args.getRaw(0));
                Location loc = sender.getLocation();
                List<String> coords = List.of(String.valueOf(loc.getBlockX()),
                                            String.valueOf(loc.getBlockY()),
                                            String.valueOf(loc.getBlockZ()));

                MessageUtils.sendMessage(sender, sendCoordsCmd.getMessages()[0], true,
                                            List.of("%target%"), List.of(player.getName()));
                MessageUtils.sendMessage(player, sendCoordsCmd.getMessages()[1], true,
                                            List.of("%sender%", "%x%", "%y%", "%z%"),
                                            List.of(sender.getName(), coords.get(0), coords.get(1), coords.get(2)));
            })
            .register();
    }
}
