package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.common.minecraft.inventories.DisposalInventory;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

public class DisposalCommand {
    public static void register() {
        CommandWrapper disposalCmd = CommandProvider.getDisposalCommand();
        new CommandAPICommand(disposalCmd.getName())
            .withOptionalArguments(PLAYERS_OPT_ARG.withPermission(
                disposalCmd.getPermission().others()
            ))
            .withFullDescription(disposalCmd.getDescription())
            .withPermission(disposalCmd.getPermission().base())
            .withShortDescription(disposalCmd.getDescription())
            .executesPlayer((sender,args) -> {
                if (args.count() > 1) {
                    MessageUtils.sendMessage(sender, MessageProvider.Errors.tooManyArguments(), true);
                } else if (args.count() == 0) {
                    Player player = sender;
                    player.openInventory(DisposalInventory.getInv());
                } else if (args.count() == 1) {
                    Player player = Bukkit.getServer().getPlayer(args.getRaw(0));
                    player.openInventory(DisposalInventory.getInv());
                }
            })
            .register();
    }
}
