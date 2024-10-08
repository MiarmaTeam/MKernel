package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.TpaRequest;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.List;
import java.util.Optional;

public class TpDenyCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.tpdeny.name"))
            .withArguments(TPA_TARGETS)
            .withPermission(MiarmaCore.CONFIG.getString("commands.tpdeny.permission"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.tpdeny.description"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.tpdeny.description"))
            .withUsage(MiarmaCore.CONFIG.getString("commands.tpdeny.usage"))
            .executesPlayer((sender, args) -> {
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

                Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.tpdeny.messages.denied"), sender, true);
                Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.tpdeny.messages.deniedToTarget"), target, true,
                        true, List.of("%sender%"), List.of(sender.getName()));
            })
            .register();
    }
}
