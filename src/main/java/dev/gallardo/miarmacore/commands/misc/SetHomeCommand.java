package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Location;

import static dev.gallardo.miarmacore.MiarmaCore.HOME_CONFIG;

import java.util.List;

public class SetHomeCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.sethome.name"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.sethome.permission"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.sethome.description"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.sethome.description"))
            .executesPlayer((sender, args) -> {
                Location loc = sender.getLocation();
                String path = "homes." + sender.getName();
                HOME_CONFIG.getConfig().set(path + ".world", loc.getWorld().getName());
                HOME_CONFIG.getConfig().set(path + ".x", loc.getX());
                HOME_CONFIG.getConfig().set(path + ".y", loc.getY());
                HOME_CONFIG.getConfig().set(path + ".z", loc.getZ());
                HOME_CONFIG.getConfig().set(path + ".yaw", loc.getYaw());
                HOME_CONFIG.getConfig().set(path + ".pitch", loc.getPitch());
                HOME_CONFIG.saveConfig();
                Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.sethome.messages.homeSet"), sender, true,
                        true, List.of("%x%", "%y%", "%z%"), List.of(loc.getX()+"", loc.getY()+"", loc.getZ()+""));
            })
            .register();
    }
}
