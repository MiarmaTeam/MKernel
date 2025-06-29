package net.miarma.mkernel.commands.misc;

import net.miarma.mkernel.config.CommandWrapper;
import net.miarma.mkernel.config.providers.CommandProvider;
import net.miarma.mkernel.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

import static net.miarma.mkernel.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

public class SpawnCommand {
    public static void register() {
        CommandWrapper spawnCmd = CommandProvider.getSpawnCommand();
        new CommandAPICommand(spawnCmd.getName())
            .withOptionalArguments(PLAYERS_OPT_ARG.withPermission(
                    spawnCmd.getPermission().others()
            ))
            .withFullDescription(spawnCmd.getDescription())
            .withPermission(spawnCmd.getPermission().base())
            .withShortDescription(spawnCmd.getDescription())
            .executesPlayer((sender, args) -> {

                double xSpawn = sender.getWorld().getSpawnLocation().getBlockX() + 0.500;
                double ySpawn = sender.getWorld().getSpawnLocation().getBlockY();
                double zSpawn = sender.getWorld().getSpawnLocation().getBlockZ() + 0.500;

                if (args.count() == 0) {
                    Location spawnCoords = new Location(sender.getWorld(), xSpawn, ySpawn, zSpawn);
                    sender.teleport(spawnCoords);
                    MessageUtil.sendMessage(sender, spawnCmd.getMessages()[0], true);
                } else if (args.count() >= 1) {
                    Player victim = Bukkit.getServer().getPlayer(args.getRaw(0));
                    Location spawnCoords = new Location(victim.getWorld(), xSpawn, ySpawn, zSpawn, victim.getLocation().getYaw(), victim.getLocation().getPitch());
                    victim.teleport(spawnCoords);

                    MessageUtil.sendMessage(sender, spawnCmd.getMessages()[1], true,
                                                 List.of("%victim%"), List.of(victim.getName()));
                    MessageUtil.sendMessage(victim, spawnCmd.getMessages()[2], true,
                                                 List.of("%sender%"), List.of(sender.getName()));
                }
            })
            .register();
    }
}
