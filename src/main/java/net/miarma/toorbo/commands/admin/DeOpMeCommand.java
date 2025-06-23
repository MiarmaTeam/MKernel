package net.miarma.toorbo.commands.admin;

import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;

public class DeOpMeCommand {
    public static void register() {
        CommandWrapper deopMeCmd = CommandProvider.getDeopMeCommand();
        new CommandAPICommand(deopMeCmd.getName())
            .withFullDescription(deopMeCmd.getDescription())
            .withShortDescription(deopMeCmd.getDescription())
            .withPermission(deopMeCmd.getPermission().base())
            .executesPlayer((sender,args) -> {
                if(sender.isOp()) {
                    sender.setOp(false);
                    MessageUtil.sendMessage(sender, deopMeCmd.getMessages()[0], true);
                } else {
                    MessageUtil.sendMessage(sender, deopMeCmd.getMessages()[1], true);
                }
            })
            .register();
    }
}
