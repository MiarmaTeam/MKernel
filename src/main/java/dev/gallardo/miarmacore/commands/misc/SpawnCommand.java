package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import dev.gallardo.miarmacore.MiarmaCore;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;
import static dev.gallardo.miarmacore.util.Constants.*;

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
                    Utils.sendMessage(spawnCmd.getMessages()[0], sender, true);
                } else if (args.count() >= 1) {
                    Player victim = Bukkit.getServer().getPlayer(args.getRaw(0));
                    Location spawnCoords = new Location(victim.getWorld(), xSpawn, ySpawn, zSpawn, victim.getLocation().getYaw(), victim.getLocation().getPitch());
                    victim.teleport(spawnCoords);

                    Utils.sendMessage(spawnCmd.getMessages()[1], sender, true,
                            true, List.of("%victim%"), List.of(victim.getName()));
                    Utils.sendMessage(spawnCmd.getMessages()[2], victim, true,
                            true, List.of("%sender%"), List.of(sender.getName()));
                }
            })
            .register();
    }
}
