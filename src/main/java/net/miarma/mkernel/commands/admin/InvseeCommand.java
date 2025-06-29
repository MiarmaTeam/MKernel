package net.miarma.mkernel.commands.admin;

import dev.jorel.commandapi.CommandAPICommand;
import net.miarma.mkernel.config.CommandWrapper;
import net.miarma.mkernel.config.providers.CommandProvider;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

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
                    MessageUtil.sendMessage(sender, invseeCommand.getMessages()[0], true,
                            List.of("%player%"), List.of(args.getRaw(0)));
                })
                .register();
    }
}
