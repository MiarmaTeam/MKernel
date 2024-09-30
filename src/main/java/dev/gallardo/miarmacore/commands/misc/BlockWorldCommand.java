package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.common.CustomConfigManager;
import dev.gallardo.miarmacore.tasks.LocationTracker;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import dev.gallardo.miarmacore.MiarmaCore;

import java.util.List;

import static dev.gallardo.miarmacore.util.Constants.*;

public class BlockWorldCommand {
    public static void register() {
        CustomConfigManager worldBlockerConfigManager = new CustomConfigManager(MiarmaCore.PLUGIN,"blockedWorlds.yml");
        List<String> blockedWorlds = worldBlockerConfigManager.getConfig().getStringList("blockedWorlds");
        new CommandAPICommand(CONFIG.getString("commands.blockworld.name"))
            .withArguments(WORLDS)
            .withAliases(CONFIG.getConfig().getStringList("commands.blockworld.aliases").toArray(new String[0]))
            .withFullDescription(CONFIG.getString("commands.blockworld.description"))
            .withPermission(CONFIG.getString("commands.blockworld.permission"))
            .withShortDescription(CONFIG.getString("commands.blockworld.description"))
            .executesPlayer((sender, args) -> {
                if (args.count() != 1) {
                    Utils.sendMessage(CONFIG.getString("language.errors.invalidArgument"), sender, true);
                }

                String world = args.getRaw(0);

                if (blockedWorlds.contains(world)) {
                    blockedWorlds.remove(world);
                    Utils.sendMessage(CONFIG.getString("commands.blockworld.messages.worldHasBeenUnblocked"), sender,
                            true, true, List.of("%world%"), List.of(world));
                } else {
                    blockedWorlds.add(world);
                    List<Player> playersInWorld = Bukkit.getWorld(world).getPlayers();
                    if(!playersInWorld.isEmpty()) {
                        playersInWorld.forEach(p -> p.teleport(LocationTracker.getPlayerLocation(p)));
                    }
                    Utils.sendMessage(CONFIG.getString("commands.blockworld.messages.worldHasBeenBlocked"), sender,
                            true, true, List.of("%world%"), List.of(world));
                }

                worldBlockerConfigManager.getConfig().set("blockedWorlds", blockedWorlds);
                worldBlockerConfigManager.saveConfig();
            })
            .register();
    }
}
