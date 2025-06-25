package net.miarma.toorbo.commands.admin;

import dev.jorel.commandapi.CommandAPICommand;
import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.config.providers.MessageProvider;
import net.miarma.toorbo.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class InvseeCommand {
    public static void register() {
        CommandWrapper invseeCommand = CommandProvider.getInvseeCommand();
        new CommandAPICommand(invseeCommand.getName())
                .withShortDescription(invseeCommand.getDescription())
                .withFullDescription(invseeCommand.getDescription())
                .withPermission(invseeCommand.getPermission().base())
                .executesPlayer((sender,args) -> {
                    Player victim = Bukkit.getPlayer(args.getRaw(0));

                    if(args.count() > 1) {
                        MessageUtil.sendMessage(sender, MessageProvider.Errors.tooManyArguments(), true);
                    }

                    PlayerInventory inv =  victim.getInventory();
                    sender.openInventory(inv);
                })
                .register();
    }
}
