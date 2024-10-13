package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.common.minecraft.TpaRequest;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.TPA_TARGETS;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.List;

public class TpDenyCommand {
    public static void register() {
        CommandWrapper tpDenyCmd = CommandProvider.getTpDenyCommand();
        new CommandAPICommand(tpDenyCmd.getName())
            .withArguments(TPA_TARGETS)
            .withPermission(tpDenyCmd.getPermission().base())
            .withFullDescription(tpDenyCmd.getDescription())
            .withShortDescription(tpDenyCmd.getDescription())
            .withUsage(tpDenyCmd.getUsage())
            .executesPlayer((sender, args) -> {
                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    MessageUtils.sendMessage(MessageProvider.Errors.playerNotFound(), sender, true);
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
                    MessageUtils.sendMessage(MessageProvider.Errors.noRequestFound(), sender, true);
                    return;
                }

                TPA_REQUESTS.removeRequest(request);

                MessageUtils.sendMessage(tpDenyCmd.getMessages()[0], sender, true);
                MessageUtils.sendMessage(tpDenyCmd.getMessages()[1], target, true,
                        true, List.of("%sender%"), List.of(sender.getName()));
            })
            .register();
    }
}
