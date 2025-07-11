package net.miarma.mkernel.commands.tp;

import net.miarma.mkernel.common.minecraft.teleport.TpaManager;
import net.miarma.mkernel.common.minecraft.teleport.TpaRequest;
import net.miarma.mkernel.config.CommandWrapper;
import net.miarma.mkernel.config.providers.CommandProvider;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.miarma.mkernel.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

import java.util.List;
import java.util.Optional;

public class TpDenyCommand {
    public static void register() {
        CommandWrapper tpDenyCmd = CommandProvider.getTpDenyCommand();
        new CommandAPICommand(tpDenyCmd.getName())
            .withOptionalArguments(PLAYERS_OPT_ARG)
            .withPermission(tpDenyCmd.getPermission().base())
            .withFullDescription(tpDenyCmd.getDescription())
            .withShortDescription(tpDenyCmd.getDescription())
            .withUsage(tpDenyCmd.getUsage())
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

                MessageUtil.sendMessage(sender, tpDenyCmd.getMessages()[0], true, List.of("%target%"), List.of(from.getName()));
                MessageUtil.sendMessage(from, tpDenyCmd.getMessages()[1], true, List.of("%sender%"), List.of(to.getName()));
            })
            .register();
    }
}
