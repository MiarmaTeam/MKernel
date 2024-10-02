package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;

import java.io.IOException;
import java.util.List;

import static dev.gallardo.miarmacore.util.Constants.INVENTORY_INDEX;

public class InventoryRecoveryCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.recinv.name"))
            .withArguments(INVENTORY_INDEX)
            .withPermission(MiarmaCore.CONFIG.getString("commands.recinv.permission"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.recinv.description"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.recinv.description"))
            .executesPlayer((sender, args) -> {
                int xpLevels = sender.getLevel();
                int requiredLevels = MiarmaCore.CONFIG.getInt("config.values.recoverInventoryRequiredLevel");
                Integer index = Integer.valueOf(args.getRaw(0));

                if (xpLevels < requiredLevels) {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.recinv.messages.notEnoughLevels"), sender, true,
                            true, List.of("%required%"),
                            List.of(String.valueOf(requiredLevels)));
                    return;
                }

                int items = 0;
                try {
                    items = Utils.restoreInventory(sender, index);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.recinv.messages.inventoryRecovered"),
                        sender, true, true, List.of("%items%"),
                        List.of(String.valueOf(items)));

                sender.setLevel(xpLevels - requiredLevels);
                Utils.clearInventory(sender);
            })
            .register();
    }
}
