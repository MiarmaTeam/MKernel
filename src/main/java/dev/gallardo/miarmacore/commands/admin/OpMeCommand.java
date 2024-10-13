package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;

public class OpMeCommand {
    public static void register() {
        CommandWrapper opMeCmd = CommandProvider.getOpMeCommand();
        new CommandAPICommand(opMeCmd.getName())
            .withFullDescription(opMeCmd.getDescription())
            .withShortDescription(opMeCmd.getDescription())
            .withPermission(opMeCmd.getPermission().base())
            .executesPlayer((sender,args) -> {
                if(!sender.isOp()) {
                    sender.setOp(true);
                    MessageUtils.sendMessage(opMeCmd.getMessages()[0], sender, true);
                } else {
                    MessageUtils.sendMessage(opMeCmd.getMessages()[1], sender, true);
                }
            })
            .register();
    }
}
