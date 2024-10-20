package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;

public class SunCommand {
    public static void register() {
        CommandWrapper sunCmd = CommandProvider.getSunCommand();
        new CommandAPICommand(sunCmd.getName())
            .withShortDescription(sunCmd.getDescription())
            .withFullDescription(sunCmd.getDescription())
            .withPermission(sunCmd.getPermission().base())
            .executesPlayer((sender,args) -> {
                sender.getWorld().setStorm(false);
                sender.getWorld().setThundering(false);
                MessageUtil.sendMessage(sender, sunCmd.getMessages()[0], true);
            })
            .register();
    }
}
