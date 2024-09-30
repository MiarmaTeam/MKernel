package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import dev.gallardo.miarmacore.MiarmaCore;

import java.util.List;

import static dev.gallardo.miarmacore.util.Constants.*;

public class LobbyCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.lobby.name"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.lobby.description"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.lobby.description"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.lobby.permissions.base"))
            .withOptionalArguments(
                PLAYERS_OPT_ARG.withPermission(
                    MiarmaCore.CONFIG.getString("commands.lobby.permissions.others")
                )
            )
            .executesPlayer((sender,args) -> {
                boolean lobbyExists = Bukkit.getServer().getWorlds().stream()
                        .map(World::getName)
                        .map(String::toLowerCase)
                        .anyMatch(w -> w.contains(MiarmaCore.CONFIG.getString("config.worlds.lobby.name")));

                if(lobbyExists) {
                    float x = MiarmaCore.CONFIG.getInt("config.worlds.lobby.x");
                    float y = MiarmaCore.CONFIG.getInt("config.worlds.lobby.y");
                    float z = MiarmaCore.CONFIG.getInt("config.worlds.lobby.z");
                    int yaw = MiarmaCore.CONFIG.getInt("config.worlds.lobby.yaw");
                    int pitch = MiarmaCore.CONFIG.getInt("config.worlds.lobby.pitch");
                    Location lobbyCoords = new Location(Bukkit.getWorld(MiarmaCore.CONFIG.getString("config.worlds.lobby.name")), x, y, z, yaw, pitch);
                    if (args.count() == 0) {
                        sender.teleport(lobbyCoords);
                        Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.lobby.messages.teleported"), sender, true);
                    } else if (args.count() >= 1) {
                        Player victim = Bukkit.getServer().getPlayer(args.getRaw(0));
                        victim.teleport(lobbyCoords);
                        Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.lobby.messages.lobbyYouOthers"), sender, true,
                                true, List.of("%victim%"), List.of(victim.getName()));
                        Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.lobby.messages.lobbyOthersYou"), victim, true,
                                true, List.of("%sender%"), List.of(sender.getName()));
                    }
                } else {
                    sender.sendMessage(MiarmaCore.CONFIG.getString("language.lobbyDoesNotExist"));
                }
            })
            .register();
    }
}
