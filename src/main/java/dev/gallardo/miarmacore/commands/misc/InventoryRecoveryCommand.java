package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;

import java.util.List;

public class InventoryRecoveryCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.recinv.name"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.recinv.permission"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.recinv.description"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.recinv.description"))
            .executesPlayer((sender, args) -> {
                int xpLevels = sender.getLevel();
                int requiredLevels = MiarmaCore.CONFIG.getInt("config.values.recoverInventoryRequiredLevel");

                if (xpLevels < requiredLevels) {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.recinv.messages.notEnoughLevels"), sender, true,
                            true, List.of("%required%"), List.of(String.valueOf(requiredLevels)));
                }

                Utils.getInventoryFromFile(sender);
                Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.recinv.messages.inventoryRecovered"),
                        sender, true);
            })
            .register();
    }
}
