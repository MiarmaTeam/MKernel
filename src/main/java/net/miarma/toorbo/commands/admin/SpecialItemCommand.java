package net.miarma.toorbo.commands.admin;

import net.miarma.toorbo.Toorbo;
import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.config.providers.MessageProvider;
import net.miarma.toorbo.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

import java.util.List;

import static net.miarma.toorbo.config.providers.CommandProvider.Arguments.ITEMS;

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
                Recipe specialItem = Bukkit.getServer().getRecipe(new NamespacedKey(Toorbo.PLUGIN, itemName));
                if (specialItem != null) {
                    sender.getInventory().addItem(specialItem.getResult());
                    MessageUtil.sendMessage(sender, specialItemCmd.getMessages()[0], true,
                                                 List.of("%item%"), List.of(itemName));
                } else {
                    MessageUtil.sendMessage(sender, MessageProvider.Errors.itemNotFound(), true,
                                                List.of("%item%"), List.of(itemName));
                }
            })
            .register();
    }
}
