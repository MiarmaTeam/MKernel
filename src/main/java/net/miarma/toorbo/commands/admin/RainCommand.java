package net.miarma.toorbo.commands.admin;

import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.util.MessageUtil;
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
