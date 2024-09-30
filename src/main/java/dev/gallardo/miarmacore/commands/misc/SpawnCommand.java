package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

import static dev.gallardo.miarmacore.util.Constants.*;

public class SpawnCommand {
    public static void register() {
        new CommandAPICommand(CONFIG.getString("commands.spawn.name"))
            .withOptionalArguments(PLAYERS_OPT_ARG.withPermission(
                    CONFIG.getString("commands.spawn.permissions.others")
            ))
            .withFullDescription(CONFIG.getString("commands.spawn.description"))
            .withPermission(CONFIG.getString("commands.spawn.permissions.base"))
            .withShortDescription(CONFIG.getString("commands.spawn.description"))
            .executesPlayer((sender, args) -> {

                double xSpawn = sender.getWorld().getSpawnLocation().getBlockX() + 0.500;
                double ySpawn = sender.getWorld().getSpawnLocation().getBlockY();
                double zSpawn = sender.getWorld().getSpawnLocation().getBlockZ() + 0.500;

                if (args.count() == 0) {
                    Location spawnCoords = new Location(sender.getWorld(), xSpawn, ySpawn, zSpawn);
                    sender.teleport(spawnCoords);
                    Utils.sendMessage(CONFIG.getString("commands.spawn.messages.teleported"), sender, true);
                } else if (args.count() >= 1) {
                    Player victim = Bukkit.getServer().getPlayer(args.getRaw(0));
                    Location spawnCoords = new Location(victim.getWorld(), xSpawn, ySpawn, zSpawn, victim.getLocation().getYaw(), victim.getLocation().getPitch());
                    victim.teleport(spawnCoords);

                    Utils.sendMessage(CONFIG.getString("commands.spawn.messages.spawnYouOthers"), sender, true,
                            true, List.of("%victim%"), List.of(victim.getName()));
                    Utils.sendMessage(CONFIG.getString("commands.spawn.messages.spawnOthersYou"), victim, true,
                            true, List.of("%sender%"), List.of(sender.getName()));
                }
            })
            .register();
    }
}
