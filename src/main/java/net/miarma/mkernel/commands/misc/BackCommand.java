package net.miarma.mkernel.commands.misc;

import dev.jorel.commandapi.CommandAPICommand;
import net.miarma.mkernel.common.minecraft.BackManager;
import net.miarma.mkernel.config.CommandWrapper;
import net.miarma.mkernel.config.providers.CommandProvider;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.MessageUtil;

public class BackCommand {
    public static void register() {
        CommandWrapper backCommand = CommandProvider.getBackCommand();
        new CommandAPICommand(backCommand.getName())
                .withFullDescription(backCommand.getDescription())
                .withPermission(backCommand.getPermission().base())
                .withShortDescription(backCommand.getDescription())
                .executesPlayer((sender,args) -> {
                    if (args.count() > 0) {
                        MessageUtil.sendMessage(sender, MessageProvider.Errors.tooManyArguments(), true);
                    }
                    sender.teleport(BackManager.getLastLocation(sender));
                })
                .register();
    }
}
