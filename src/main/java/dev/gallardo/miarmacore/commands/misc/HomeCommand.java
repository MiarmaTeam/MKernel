package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import static dev.gallardo.miarmacore.MiarmaCore.HOME_CONFIG;

public class HomeCommand {
    public static void register() {
        CommandWrapper homeCmd = CommandProvider.getHomeCommand();
        new CommandAPICommand(homeCmd.getName())
            .withPermission(homeCmd.getPermission().base())
            .withFullDescription(homeCmd.getDescription())
            .withShortDescription(homeCmd.getDescription())
            .executesPlayer((sender, args) -> {
                String path = "homes." + sender.getName();
                if(HOME_CONFIG.getConfig().contains(path)) {
                    World world = Bukkit.getWorld(HOME_CONFIG.getConfig().getString(path + ".world"));
                    double x = HOME_CONFIG.getConfig().getDouble(path + ".x");
                    double y = HOME_CONFIG.getConfig().getDouble(path + ".y");
                    double z = HOME_CONFIG.getConfig().getDouble(path + ".z");
                    float yaw = (float) HOME_CONFIG.getConfig().getDouble(path + ".yaw");
                    float pitch = (float) HOME_CONFIG.getConfig().getDouble(path + ".pitch");
                    Location loc = new Location(world, x, y, z, yaw, pitch);
                    sender.teleport(loc);
                    Utils.sendMessage(homeCmd.getMessages()[0], sender, true);
                } else {
                    Utils.sendMessage(homeCmd.getMessages()[1], sender, true);
                }
            })
            .register();
    }
}
