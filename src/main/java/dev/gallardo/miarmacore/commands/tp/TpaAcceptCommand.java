package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.common.minecraft.TpaRequest;
import dev.gallardo.miarmacore.common.minecraft.TpaRequests;
import dev.gallardo.miarmacore.common.minecraft.TpaType;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.TPA_TARGETS;
import static dev.gallardo.miarmacore.util.Constants.*;

import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class TpaAcceptCommand {
    public static void register() {
        CommandWrapper tpaAcceptCmd = CommandProvider.getTpaAcceptCommand();
        new CommandAPICommand(tpaAcceptCmd.getName())
            .withArguments(TPA_TARGETS)
            .withPermission(tpaAcceptCmd.getPermission().base())
            .withFullDescription(tpaAcceptCmd.getDescription())
            .withShortDescription(tpaAcceptCmd.getDescription())
            .withUsage(tpaAcceptCmd.getUsage())
            .executesPlayer((sender, args) -> {
                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    MessageUtils.sendMessage(sender, MessageProvider.Errors.playerNotFound(), true);
                    return;
                }

                TpaRequest request;
                if(TpaRequests.getInstance().getTpaRequest(target, sender) != null) {
                    request = TpaRequests.getInstance().getTpaRequest(target, sender);
                } else {
                    request = TpaRequests.getInstance().getTpaHereRequest(target, sender);
                }

                if (request == null) {
                    MessageUtils.sendMessage(sender, MessageProvider.Errors.noRequestFound(), true);
                    return;
                }

                TpaRequests.getInstance().removeRequest(request);

                if (request.type() == TpaType.TPA) {
                    target.teleport(sender.getLocation());
                } else if (request.type() == TpaType.TPA_HERE) {
                    sender.teleport(target.getLocation());
                }

                MessageUtils.sendMessage(target, tpaAcceptCmd.getMessages()[1], true,
                                            List.of("%sender%"), List.of(sender.getName()));
                MessageUtils.sendMessage(sender, tpaAcceptCmd.getMessages()[0], true,
                                            List.of("%target%"), List.of(target.getName()));
            })
            .register();
    }
}
