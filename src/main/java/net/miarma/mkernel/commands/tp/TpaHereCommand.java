package net.miarma.mkernel.commands.tp;

import net.miarma.mkernel.MKernel;
import net.miarma.mkernel.common.minecraft.teleport.TpaManager;
import net.miarma.mkernel.common.minecraft.teleport.TpaType;
import net.miarma.mkernel.config.CommandWrapper;
import net.miarma.mkernel.config.providers.CommandProvider;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

import static net.miarma.mkernel.config.providers.CommandProvider.Arguments.PLAYER_ARG;

public class TpaHereCommand {
    public static void register() {
        CommandWrapper tpaHereCmd = CommandProvider.getTpaHereCommand();
        new CommandAPICommand(tpaHereCmd.getName())
                .withArguments(PLAYER_ARG)
                .withPermission(tpaHereCmd.getPermission().base())
                .withFullDescription(tpaHereCmd.getDescription())
                .withShortDescription(tpaHereCmd.getDescription())
                .withUsage(tpaHereCmd.getUsage())
                .executesPlayer((sender, args) -> {
                    Player target = Bukkit.getPlayer(args.getRaw(0));

                    if (target == null || !target.isOnline()) {
                        MessageUtil.sendMessage(sender, MessageProvider.Errors.playerNotFound(), true);
                        return;
                    }
                    if (sender.equals(target)) {
                        MessageUtil.sendMessage(sender, MessageProvider.Errors.cantTeleportToYourself(), true);
                        return;
                    }
                    if (TpaManager.getInstance().getRequest(target).isPresent()) {
                        MessageUtil.sendMessage(sender, MessageProvider.Errors.requestAlreadySent(), true);
                        return;
                    }

                    TpaManager.getInstance().sendRequest(sender, target, TpaType.TPA_HERE);

                    MessageUtil.sendMessage(sender, tpaHereCmd.getMessages()[1], true,
                            List.of("%target%"), List.of(target.getName()));
                    MessageUtil.sendMessage(target, tpaHereCmd.getMessages()[0], true,
                            List.of("%sender%"), List.of(sender.getName()));
                })
                .register();
    }
}
