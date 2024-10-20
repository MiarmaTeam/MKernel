package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;

public class RainCommand {
    public static void register() {
        CommandWrapper rainCmd = CommandProvider.getRainCommand();
        new CommandAPICommand(rainCmd.getName())
            .withShortDescription(rainCmd.getDescription())
            .withFullDescription(rainCmd.getDescription())
            .withPermission(rainCmd.getPermission().base())
            .executesPlayer((sender,args) -> {
                sender.getWorld().setStorm(true);
                sender.getWorld().setThundering(false);
                MessageUtil.sendMessage(sender, rainCmd.getMessages()[0], true);
            })
            .register();
    }
}
