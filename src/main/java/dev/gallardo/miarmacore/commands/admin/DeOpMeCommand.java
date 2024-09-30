package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;

import static dev.gallardo.miarmacore.util.Constants.*;

public class DeOpMeCommand {
    public static void register() {
        new CommandAPICommand(CONFIG.getString("commands.deopme.name"))
            .withFullDescription(CONFIG.getString("commands.deopme.description"))
            .withShortDescription(CONFIG.getString("commands.deopme.description"))
            .withPermission(CONFIG.getString("commands.deopme.permission"))
            .executesPlayer((sender,args) -> {
                if(sender.isOp()) {
                    sender.setOp(false);
                    Utils.sendMessage(CONFIG.getString("commands.deopme.messages.deOpped"), sender, true);
                } else {
                    Utils.sendMessage(CONFIG.getString("commands.deopme.messages.youAreNotOp"), sender, true);
                }
            })
            .register();
    }
}
