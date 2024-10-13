package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.ConfigProvider;
import dev.gallardo.miarmacore.util.FileUtils;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;

import java.io.IOException;
import java.util.List;

public class InventoryRecoveryCommand {
    public static void register() {
        CommandWrapper recInvCmd = CommandProvider.getRecInvCommand();
        new CommandAPICommand(recInvCmd.getName())
            .withPermission(recInvCmd.getPermission().base())
            .withShortDescription(recInvCmd.getDescription())
            .withFullDescription(recInvCmd.getDescription())
            .executesPlayer((sender, args) -> {
                int xpLevels = sender.getLevel();
                int requiredLevels = ConfigProvider.Values.getRecInvRequiredLevel();

                if (xpLevels < requiredLevels) {
                    MessageUtils.sendMessage(sender, recInvCmd.getMessages()[1], true,
                                                 List.of("%required%"), List.of(String.valueOf(requiredLevels)));
                    return;
                }

                int items;
                try {
                    items = FileUtils.restoreInventory(sender);
                    if(items == 0) {
                        MessageUtils.sendMessage(sender, recInvCmd.getMessages()[2], true);
                        return;
                    }
                    MessageUtils.sendMessage(sender, recInvCmd.getMessages()[0], true,
                                                List.of("%items%"), List.of(String.valueOf(items)));
                    sender.setLevel(xpLevels - requiredLevels);
                    FileUtils.clearInventory(sender);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            })
            .register();
    }
}
