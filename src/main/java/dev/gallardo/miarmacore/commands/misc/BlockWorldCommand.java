package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.tasks.LocationTracker;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dev.gallardo.miarmacore.MiarmaCore.WORLD_BLOCKER_CONFIG;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.WORLDS;

public class BlockWorldCommand {
    public static void register() {
        CommandWrapper blockWorldCmd = CommandProvider.getBlockWorldCommand();
        List<String> blockedWorlds = WORLD_BLOCKER_CONFIG.getConfig().getStringList("blockedWorlds");
        new CommandAPICommand(blockWorldCmd.getName())
            .withArguments(WORLDS)
            .withAliases(blockWorldCmd.getAliases())
            .withFullDescription(blockWorldCmd.getDescription())
            .withPermission(blockWorldCmd.getPermission().base())
            .withShortDescription(blockWorldCmd.getDescription())
            .executesPlayer((sender, args) -> {
                if (args.count() != 1) {
                    MessageUtils.sendMessage(sender, MessageProvider.Errors.invalidArgument(), true);
                }

                String world = args.getRaw(0);

                if (blockedWorlds.contains(world)) {
                    blockedWorlds.remove(world);
                    MessageUtils.sendMessage(sender, blockWorldCmd.getMessages()[1], true,
                                                List.of("%world%"), List.of(world));
                } else {
                    blockedWorlds.add(world);
                    List<Player> playersInWorld = Bukkit.getWorld(world).getPlayers();
                    if(!playersInWorld.isEmpty()) {
                        playersInWorld.forEach(p -> p.teleport(LocationTracker.getPlayerLocation(p)));
                    }
                    MessageUtils.sendMessage(sender, blockWorldCmd.getMessages()[0], true,
                                                List.of("%world%"), List.of(world));
                }

                WORLD_BLOCKER_CONFIG.getConfig().set("blockedWorlds", blockedWorlds);
                WORLD_BLOCKER_CONFIG.saveConfig();
            })
            .register();
    }
}
