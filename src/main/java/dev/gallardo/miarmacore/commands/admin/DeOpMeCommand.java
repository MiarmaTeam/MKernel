package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtil;
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
