package net.miarma.toorbo.commands.misc;

import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.config.providers.ConfigProvider;
import net.miarma.toorbo.config.providers.MessageProvider;
import net.miarma.toorbo.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import net.miarma.toorbo.Toorbo;

import java.util.List;

import static net.miarma.toorbo.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

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
                        MessageUtil.sendMessage(sender, lobbyCmd.getMessages()[0], true);
                    } else if (args.count() >= 1) {
                        Player victim = Bukkit.getServer().getPlayer(args.getRaw(0));
                        victim.teleport(lobbyCoords);
                        MessageUtil.sendMessage(sender, lobbyCmd.getMessages()[1], true,
                                                    List.of("%victim%"), List.of(victim.getName()));
                        MessageUtil.sendMessage(victim, lobbyCmd.getMessages()[2], true,
                                                    List.of("%sender%"), List.of(sender.getName()));
                    }
                } else {
                    MessageUtil.sendMessage(sender, MessageProvider.Errors.lobbyDoesNotExist(), true);
                    Toorbo.LOGGER.warning(MessageUtil.formatMessageConsole(
                            MessageProvider.Errors.lobbyDoesNotExist(), true));
                }
            })
            .register();
    }
}
