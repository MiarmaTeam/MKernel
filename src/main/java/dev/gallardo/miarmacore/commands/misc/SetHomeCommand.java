package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Location;

import static dev.gallardo.miarmacore.MiarmaCore.HOME_CONFIG;

import java.util.List;

public class SetHomeCommand {
    public static void register() {
        CommandWrapper setHomeCmd = CommandProvider.getSetHomeCommand();
        new CommandAPICommand(setHomeCmd.getName())
            .withPermission(setHomeCmd.getPermission().base())
            .withFullDescription(setHomeCmd.getDescription())
            .withShortDescription(setHomeCmd.getDescription())
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
                MessageUtil.sendMessage(sender, setHomeCmd.getMessages()[0], true,
                                            List.of("%x%", "%y%", "%z%"),
                                            List.of((int)loc.getX()+"", (int)loc.getY()+"", (int)loc.getZ()+""));
            })
            .register();
    }
}
