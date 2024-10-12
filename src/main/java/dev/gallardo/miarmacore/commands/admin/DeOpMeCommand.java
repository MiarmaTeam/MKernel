package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.Utils;
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
                    Utils.sendMessage(deopMeCmd.getMessages()[0], sender, true);
                } else {
                    Utils.sendMessage(deopMeCmd.getMessages()[1], sender, true);
                }
            })
            .register();
    }
}
