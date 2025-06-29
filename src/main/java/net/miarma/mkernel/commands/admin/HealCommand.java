package net.miarma.mkernel.commands.admin;

import net.miarma.mkernel.config.CommandWrapper;
import net.miarma.mkernel.config.providers.CommandProvider;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

import static net.miarma.mkernel.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

public class HealCommand {
    public static void register() {
        CommandWrapper healCmd = CommandProvider.getHealCommand();
        new CommandAPICommand(healCmd.getName())
                .withOptionalArguments(
                    PLAYERS_OPT_ARG.withPermission(healCmd.getPermission().others())
                )
                .withPermission(healCmd.getPermission().base())
                .withFullDescription(healCmd.getDescription())
                .withShortDescription(healCmd.getDescription())
                .withUsage(healCmd.getUsage())
            .executesPlayer((sender, args) -> {
                if(args.count() == 0) {
                    sender.setHealth(20);
                    sender.setFoodLevel(20);
                    MessageUtil.sendMessage(sender, healCmd.getMessages()[0], true);
                }

                if(args.count() == 1) {
                    Player target = Bukkit.getPlayer(args.getRaw(0));

                    if(target == null) {
                        MessageUtil.sendMessage(sender, MessageProvider.Errors.playerNotFound(), true);
                    }

                    target.setHealth(20);
                    target.setFoodLevel(20);

                    MessageUtil.sendMessage(sender, healCmd.getMessages()[1], true,
                            List.of("%player%"), List.of(target.getName()));
                    MessageUtil.sendMessage(target, healCmd.getMessages()[2], true,
                            List.of("%sender%"), List.of(sender.getName()));
                }
            })
            .register();
    }
}
