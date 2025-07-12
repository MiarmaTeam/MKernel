package net.miarma.mkernel.commands.misc;

import net.miarma.mkernel.common.minecraft.BackManager;
import net.miarma.mkernel.config.CommandWrapper;
import net.miarma.mkernel.config.providers.CommandProvider;
import net.miarma.mkernel.config.providers.ConfigProvider;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import net.miarma.mkernel.MKernel;

import java.util.List;

import static net.miarma.mkernel.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

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
                        BackManager.setLastLocation(sender, sender.getLocation());
                        sender.teleport(lobbyCoords);
                        MessageUtil.sendMessage(sender, lobbyCmd.getMessages()[0], true);
                    } else if (args.count() >= 1) {
                        Player victim = Bukkit.getServer().getPlayer(args.getRaw(0));
                        BackManager.setLastLocation(victim, victim.getLocation());
                        victim.teleport(lobbyCoords);
                        MessageUtil.sendMessage(sender, lobbyCmd.getMessages()[1], true,
                                                    List.of("%victim%"), List.of(victim.getName()));
                        MessageUtil.sendMessage(victim, lobbyCmd.getMessages()[2], true,
                                                    List.of("%sender%"), List.of(sender.getName()));
                    }
                } else {
                    MessageUtil.sendMessage(sender, MessageProvider.Errors.lobbyDoesNotExist(), true);
                    MKernel.LOGGER.warning(MessageUtil.formatMessageConsole(
                            MessageProvider.Errors.lobbyDoesNotExist(), true));
                }
            })
            .register();
    }
}
