package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import dev.gallardo.miarmacore.MiarmaCore;

import static dev.gallardo.miarmacore.util.Constants.*;

public class DeOpMeCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.deopme.name"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.deopme.description"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.deopme.description"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.deopme.permission"))
            .executesPlayer((sender,args) -> {
                if(sender.isOp()) {
                    sender.setOp(false);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.deopme.messages.deOpped"), sender, true);
                } else {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.deopme.messages.youAreNotOp"), sender, true);
                }
            })
            .register();
    }
}
