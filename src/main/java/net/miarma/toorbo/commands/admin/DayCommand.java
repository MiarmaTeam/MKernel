package net.miarma.toorbo.commands.admin;

import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.util.MessageUtil;
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
