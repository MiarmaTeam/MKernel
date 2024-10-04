package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;

import java.io.IOException;
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
                            true, List.of("%required%"),
                            List.of(String.valueOf(requiredLevels)));
                    return;
                }

                int items;
                try {
                    items = Utils.restoreInventory(sender);
                    if(items == 0) {
                        Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.recinv.messages.noItemsToRecover"),
                                sender, true);
                        return;
                    }
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.recinv.messages.inventoryRecovered"),
                            sender, true, true, List.of("%items%"),
                            List.of(String.valueOf(items)));
                    sender.setLevel(xpLevels - requiredLevels);
                    Utils.clearInventory(sender);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            })
            .register();
    }
}
