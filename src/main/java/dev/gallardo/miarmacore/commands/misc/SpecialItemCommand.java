package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;

import java.util.List;

import static dev.gallardo.miarmacore.util.Constants.*;

public class SpecialItemCommand {
    public static void register() {
        new CommandAPICommand(CONFIG.getString("commands.specialitem.name"))
            .withArguments(ITEMS)
            .withFullDescription(CONFIG.getString("commands.specialitem.description"))
            .withPermission(CONFIG.getString("commands.specialitem.permission"))
            .withShortDescription(CONFIG.getString("commands.specialitem.description"))
            .executesPlayer((sender, args) -> {
                String itemName = args.getRaw(0);
                Recipe specialItem = Bukkit.getServer().getRecipe(new NamespacedKey(MiarmaCore.PLUGIN, itemName));
                if (specialItem != null) {
                    sender.getInventory().addItem(specialItem.getResult());
                    Utils.sendMessage(CONFIG.getString("commands.specialitem.messages.itemReceived"), sender, true,
                             true, List.of("%item%"), List.of(itemName));
                } else {
                    Utils.sendMessage(CONFIG.getString("language.errors.itemNotFound"), sender, true,
                            true, List.of("%item%"), List.of(itemName));
                }
            })
            .register();
    }
}
