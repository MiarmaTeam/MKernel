package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.ITEMS;

public class SpecialItemCommand {
    public static void register() {
        CommandWrapper specialItemCmd = CommandProvider.getSpecialItemCommand();
        new CommandAPICommand(specialItemCmd.getName())
            .withArguments(ITEMS)
            .withFullDescription(specialItemCmd.getDescription())
            .withPermission(specialItemCmd.getPermission().base())
            .withShortDescription(specialItemCmd.getDescription())
            .executesPlayer((sender, args) -> {
                String itemName = args.getRaw(0);
                Recipe specialItem = Bukkit.getServer().getRecipe(new NamespacedKey(MiarmaCore.PLUGIN, itemName));
                if (specialItem != null) {
                    sender.getInventory().addItem(specialItem.getResult());
                    MessageUtils.sendMessage(specialItemCmd.getMessages()[0], sender, true,
                             true, List.of("%item%"), List.of(itemName));
                } else {
                    MessageUtils.sendMessage(MessageProvider.Errors.itemNotFound(), sender, true,
                            true, List.of("%item%"), List.of(itemName));
                }
            })
            .register();
    }
}
