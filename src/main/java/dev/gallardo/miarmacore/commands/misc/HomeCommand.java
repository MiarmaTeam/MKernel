package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import static dev.gallardo.miarmacore.MiarmaCore.HOME_CONFIG;
import static dev.gallardo.miarmacore.util.Constants.HOME_ARG;

public class HomeCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.home.name"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.home.permission"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.home.description"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.home.description"))
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
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.home.messages.teleported"), sender, true);
                } else {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.home.messages.homeDoesNotExist"), sender, true);
                }
            })
            .register();
    }
}
