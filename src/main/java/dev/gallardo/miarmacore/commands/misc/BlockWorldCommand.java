package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.tasks.LocationTracker;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import dev.gallardo.miarmacore.MiarmaCore;
import static dev.gallardo.miarmacore.MiarmaCore.WORLD_BLOCKER_CONFIG;

import java.util.List;

import static dev.gallardo.miarmacore.util.Constants.*;

public class BlockWorldCommand {
    public static void register() {
        List<String> blockedWorlds = WORLD_BLOCKER_CONFIG.getConfig().getStringList("blockedWorlds");
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.blockworld.name"))
            .withArguments(WORLDS)
            .withAliases(MiarmaCore.CONFIG.getConfig().getStringList("commands.blockworld.aliases").toArray(new String[0]))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.blockworld.description"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.blockworld.permission"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.blockworld.description"))
            .executesPlayer((sender, args) -> {
                if (args.count() != 1) {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.invalidArgument"), sender, true);
                }

                String world = args.getRaw(0);

                if (blockedWorlds.contains(world)) {
                    blockedWorlds.remove(world);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.blockworld.messages.worldHasBeenUnblocked"), sender,
                            true, true, List.of("%world%"), List.of(world));
                } else {
                    blockedWorlds.add(world);
                    List<Player> playersInWorld = Bukkit.getWorld(world).getPlayers();
                    if(!playersInWorld.isEmpty()) {
                        playersInWorld.forEach(p -> p.teleport(LocationTracker.getPlayerLocation(p)));
                    }
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.blockworld.messages.worldHasBeenBlocked"), sender,
                            true, true, List.of("%world%"), List.of(world));
                }

                WORLD_BLOCKER_CONFIG.getConfig().set("blockedWorlds", blockedWorlds);
                WORLD_BLOCKER_CONFIG.saveConfig();
            })
            .register();
    }
}
