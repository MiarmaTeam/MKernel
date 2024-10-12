package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.TpaRequest;
import dev.gallardo.miarmacore.common.minecraft.TpaType;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.Utils;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.TPA_TARGETS;
import static dev.gallardo.miarmacore.util.Constants.*;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

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
                    Utils.sendMessage(MessageProvider.Errors.playerNotFound(), sender, true);
                    return;
                }

                TpaRequest request = TPA_REQUESTS.getRequests().stream()
                        .filter(req -> {
                            if (req.from().equals(sender) && req.to().equals(target)) {
                                return true;
                            } else if (req.from().equals(target) && req.to().equals(sender)) {
                                return true;
                            }
                            return false;
                        })
                        .findFirst()
                        .orElse(null);

                if (request == null) {
                    Utils.sendMessage(MessageProvider.Errors.noRequestFound(), sender, true);
                    return;
                }

                TPA_REQUESTS.removeRequest(request);

                if (request.type() == TpaType.TPA) {
                    target.teleport(sender.getLocation());
                } else if (request.type() == TpaType.TPA_HERE) {
                    sender.teleport(target.getLocation());
                }

                Utils.sendMessage(tpaAcceptCmd.getMessages()[1], target, true,
                        true, List.of("%sender%"), List.of(sender.getName()));
                Utils.sendMessage(tpaAcceptCmd.getMessages()[0], sender, true);
            })
            .register();
    }
}
