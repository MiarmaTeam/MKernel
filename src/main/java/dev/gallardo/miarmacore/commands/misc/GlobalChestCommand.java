package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.common.minecraft.GlobalChest;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

public class GlobalChestCommand {
    public static void register() {
        CommandWrapper globalChestCmd = CommandProvider.getGlobalChestCommand();
        new CommandAPICommand(globalChestCmd.getName())
            .withOptionalArguments(PLAYERS_OPT_ARG.withPermission(
                    globalChestCmd.getPermission().others()
            ))
            .withFullDescription(globalChestCmd.getDescription())
            .withPermission(globalChestCmd.getPermission().base())
            .withShortDescription(globalChestCmd.getDescription())
            .executesPlayer((sender, args) -> {
                if (args.count() > 1) {
                    MessageUtils.sendMessage(sender, MessageProvider.Errors.tooManyArguments(), true);
                } else if (args.count() == 0) {
                    Player player = sender;
                    player.openInventory(GlobalChest.getInv());
                } else if (args.count() == 1) {
                    Player player = Bukkit.getServer().getPlayer(args.getRaw(0));
                    player.openInventory(GlobalChest.getInv());
                }
            })
            .register();
    }
}
