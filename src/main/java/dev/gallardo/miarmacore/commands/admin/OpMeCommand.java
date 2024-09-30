package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import dev.gallardo.miarmacore.MiarmaCore;

import static dev.gallardo.miarmacore.util.Constants.*;

public class OpMeCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.opme.name"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.opme.description"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.opme.description"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.opme.permission"))
            .executesPlayer((sender,args) -> {
                if(!sender.isOp()) {
                    sender.setOp(true);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.opme.messages.opped"), sender, true);
                } else {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.opme.messages.alreadyOp"), sender, true);
                }
            })
            .register();
    }
}
