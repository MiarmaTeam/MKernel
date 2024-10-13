package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.ConfigProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import dev.gallardo.miarmacore.MiarmaCore;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

public class LobbyCommand {
    public static void register() {
        CommandWrapper lobbyCmd = CommandProvider.getLobbyCommand();
        new CommandAPICommand(lobbyCmd.getName())
            .withFullDescription(lobbyCmd.getDescription())
            .withShortDescription(lobbyCmd.getDescription())
            .withPermission(lobbyCmd.getPermission().base())
            .withOptionalArguments(
                PLAYERS_OPT_ARG.withPermission(
                    lobbyCmd.getPermission().others()
                )
            )
            .executesPlayer((sender,args) -> {
                boolean lobbyExists = Bukkit.getServer().getWorlds().stream()
                        .map(World::getName)
                        .map(String::toLowerCase)
                        .anyMatch(w -> w.contains(ConfigProvider.Worlds.getLobby().name()));

                if(lobbyExists) {
                    String name = ConfigProvider.Worlds.getLobby().name();
                    double x = ConfigProvider.Worlds.getLobby().x();
                    double y = ConfigProvider.Worlds.getLobby().y();
                    double z = ConfigProvider.Worlds.getLobby().z();
                    int yaw = ConfigProvider.Worlds.getLobby().yaw();
                    int pitch = ConfigProvider.Worlds.getLobby().pitch();
                    Location lobbyCoords = new Location(Bukkit.getWorld(name), x, y, z, yaw, pitch);

                    if (args.count() == 0) {
                        sender.teleport(lobbyCoords);
                        MessageUtils.sendMessage(sender, lobbyCmd.getMessages()[0], true);
                    } else if (args.count() >= 1) {
                        Player victim = Bukkit.getServer().getPlayer(args.getRaw(0));
                        victim.teleport(lobbyCoords);
                        MessageUtils.sendMessage(sender, lobbyCmd.getMessages()[1], true,
                                                    List.of("%victim%"), List.of(victim.getName()));
                        MessageUtils.sendMessage(victim, lobbyCmd.getMessages()[2], true,
                                                    List.of("%sender%"), List.of(sender.getName()));
                    }
                } else {
                    MessageUtils.sendMessage(sender, MessageProvider.Errors.lobbyDoesNotExist(), true);
                    MiarmaCore.LOGGER.warning(MessageUtils.formatMessageConsole(
                            MessageProvider.Errors.lobbyDoesNotExist(), true));
                }
            })
            .register();
    }
}
