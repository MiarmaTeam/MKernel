package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.TpaRequest;
import dev.gallardo.miarmacore.common.minecraft.TpaType;
import dev.gallardo.miarmacore.util.Utils;
import static dev.gallardo.miarmacore.util.Constants.*;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class TpaAcceptCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.tpaccept.name"))
            .withArguments(TPA_TARGETS)
            .withPermission("commands.tpaccept.permission")
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.tpaccept.description"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.tpaccept.description"))
            .withUsage(MiarmaCore.CONFIG.getString("commands.tpaccept.usage"))
            .executesPlayer((sender, args) -> {
                MiarmaCore.LOGGER.info(TPA_REQUESTS.toString());

                Player target = Bukkit.getPlayer(args.getRaw(0));
                if (target == null || !target.isOnline()) {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.playerNotFound"), sender, true);
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
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.noRequestFound"), sender, true);
                    return;
                }

                TPA_REQUESTS.removeRequest(request);

                if (request.type() == TpaType.TPA) {
                    sender.teleport(target.getLocation());
                } else if (request.type() == TpaType.TPA_HERE) {
                    target.teleport(sender.getLocation());
                }

                Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.tpaccept.messages.acceptedToTarget"), target, true,
                        true, List.of("%sender%"), List.of(sender.getName()));
                Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.tpaccept.messages.accepted"), sender, true);
            })
            .register();
    }
}
