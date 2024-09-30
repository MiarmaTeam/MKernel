package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;

import static dev.gallardo.miarmacore.util.Constants.*;

public class OpMeCommand {
    public static void register() {
        new CommandAPICommand(CONFIG.getString("commands.opme.name"))
            .withFullDescription(CONFIG.getString("commands.opme.description"))
            .withShortDescription(CONFIG.getString("commands.opme.description"))
            .withPermission(CONFIG.getString("commands.opme.permission"))
            .executesPlayer((sender,args) -> {
                if(!sender.isOp()) {
                    sender.setOp(true);
                    Utils.sendMessage(CONFIG.getString("commands.opme.messages.opped"), sender, true);
                } else {
                    Utils.sendMessage(CONFIG.getString("commands.opme.messages.alreadyOp"), sender, true);
                }
            })
            .register();
    }
}
