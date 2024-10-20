package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;

public class DayCommand {
    public static void register() {
        CommandWrapper dayCmd = CommandProvider.getDayCommand();
        new CommandAPICommand(dayCmd.getName())
            .withShortDescription(dayCmd.getDescription())
            .withFullDescription(dayCmd.getDescription())
            .withPermission(dayCmd.getPermission().base())
            .executesPlayer((sender,args) -> {
                sender.getWorld().setTime(0);
                MessageUtil.sendMessage(sender, dayCmd.getMessages()[0], true);
            })
            .register();
    }
}
