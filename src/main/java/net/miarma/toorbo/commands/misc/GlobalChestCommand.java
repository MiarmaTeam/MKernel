package net.miarma.toorbo.commands.misc;

import net.miarma.toorbo.common.minecraft.inventories.GlobalChest;
import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.config.providers.MessageProvider;
import net.miarma.toorbo.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.miarma.toorbo.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

public class GlobalChestCommand {
    public static void register() {
        CommandWrapper globalChestCmd = CommandProvider.getGlobalChestCommand();
        new CommandAPICommand(globalChestCmd.getName())
            .withOptionalArguments(PLAYERS_OPT_ARG.withPermission(
                    globalChestCmd.getPermission().others()
            ))
            .withFullDescription(globalChestCmd.getDescription())
            .withAliases(globalChestCmd.getAliases())
            .withPermission(globalChestCmd.getPermission().base())
            .withShortDescription(globalChestCmd.getDescription())
            .executesPlayer((sender, args) -> {
                if (args.count() > 1) {
                    MessageUtil.sendMessage(sender, MessageProvider.Errors.tooManyArguments(), true);
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
