package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;

public class ThunderCommand {
    public static void register() {
        CommandWrapper thunderCmd = CommandProvider.getThunderCommand();
        new CommandAPICommand(thunderCmd.getName())
            .withShortDescription(thunderCmd.getDescription())
            .withFullDescription(thunderCmd.getDescription())
            .withPermission(thunderCmd.getPermission().base())
            .executesPlayer((sender,args) -> {
                sender.getWorld().setStorm(true);
                sender.getWorld().setThundering(true);
                MessageUtil.sendMessage(sender, thunderCmd.getMessages()[0], true);
            })
            .register();
    }
}
