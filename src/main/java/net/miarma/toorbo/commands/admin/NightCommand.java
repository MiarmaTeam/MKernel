package net.miarma.toorbo.commands.admin;

import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;

public class NightCommand {
    public static void register() {
        CommandWrapper nightCmd = CommandProvider.getNightCommand();
        new CommandAPICommand(nightCmd.getName())
            .withShortDescription(nightCmd.getDescription())
            .withFullDescription(nightCmd.getDescription())
            .withPermission(nightCmd.getPermission().base())
            .executesPlayer((sender,args) -> {
                sender.getWorld().setTime(13000);
                MessageUtil.sendMessage(sender, nightCmd.getMessages()[0], true);
            })
            .register();
    }
}
