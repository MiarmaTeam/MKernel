package net.miarma.mkernel.commands.tp;

import net.miarma.mkernel.common.minecraft.BackManager;
import net.miarma.mkernel.common.minecraft.teleport.TpaManager;
import net.miarma.mkernel.common.minecraft.teleport.TpaRequest;
import net.miarma.mkernel.common.minecraft.teleport.TpaType;
import net.miarma.mkernel.config.CommandWrapper;
import net.miarma.mkernel.config.providers.CommandProvider;
import net.miarma.mkernel.config.providers.MessageProvider;

import static net.miarma.mkernel.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

import net.miarma.mkernel.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class TpaAcceptCommand {
    public static void register() {
        CommandWrapper tpaAcceptCmd = CommandProvider.getTpaAcceptCommand();
        new CommandAPICommand(tpaAcceptCmd.getName())
            .withOptionalArguments(PLAYERS_OPT_ARG)
            .withPermission(tpaAcceptCmd.getPermission().base())
            .withFullDescription(tpaAcceptCmd.getDescription())
            .withShortDescription(tpaAcceptCmd.getDescription())
            .withUsage(tpaAcceptCmd.getUsage())
            .executesPlayer((sender, args) -> {
                Optional<TpaRequest> opt = TpaManager.getInstance().getRequest(sender);
                if (opt.isEmpty()) {
                    MessageUtil.sendMessage(sender, MessageProvider.Errors.noRequestFound(), true);
                    return;
                }

                TpaRequest req = opt.get();
                Player from = req.getFrom();
                Player to = req.getTo();

                TpaManager.getInstance().clearRequest(to);
                req.markUsed();

                if (req.getType() == TpaType.TPA) {
                    BackManager.setLastLocation(from, from.getLocation());
                    from.teleport(to.getLocation());
                } else {
                    BackManager.setLastLocation(to, to.getLocation());
                    to.teleport(from.getLocation());
                }

                MessageUtil.sendMessage(from, tpaAcceptCmd.getMessages()[1], true, List.of("%sender%"), List.of(to.getName()));
                MessageUtil.sendMessage(to, tpaAcceptCmd.getMessages()[0], true, List.of("%target%"), List.of(from.getName()));
            })
            .register();
    }
}
