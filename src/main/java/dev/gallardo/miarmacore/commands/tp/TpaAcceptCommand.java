package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.TpaRequest;
import dev.gallardo.miarmacore.common.minecraft.TpaType;
import dev.gallardo.miarmacore.util.Utils;
import static dev.gallardo.miarmacore.util.Constants.*;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class TpaAcceptCommand {
    public static void register() {
        new CommandAPICommand(CONFIG.getString("commands.tpaccept.name"))
            .withArguments(PLAYER_ARG)
            .withPermission("commands.tpaccept.permission")
            .withFullDescription(CONFIG.getString("commands.tpaccept.description"))
            .withShortDescription(CONFIG.getString("commands.tpaccept.description"))
            .withUsage(CONFIG.getString("commands.tpaccept.usage"))
            .executesPlayer((sender, args) -> {
                LOGGER.info(TPA_REQUESTS.toString());

                Player target = Bukkit.getPlayer(args.getRaw(0));
                if (target == null || !target.isOnline()) {
                    Utils.sendMessage(CONFIG.getString("language.errors.playerNotFound"), sender, true);
                    return;
                }

                Optional<TpaRequest> requestOpt = TPA_REQUESTS.getRequests().stream()
                        .filter(request ->
                                request.from().equals(target) && request.to().equals(sender)
                        )
                        .findFirst();

                if (requestOpt.isEmpty()) {
                    Utils.sendMessage(CONFIG.getString("language.errors.noRequestFound"), sender, true);
                    return;
                }

                TpaRequest request = requestOpt.get();
                TPA_REQUESTS.removeRequest(request);

                if (request.type() == TpaType.TPA) {
                    sender.teleport(target.getLocation());
                } else if (request.type() == TpaType.TPA_HERE) {
                    target.teleport(sender.getLocation());
                }

                Utils.sendMessage(CONFIG.getString("commands.tpaccept.messages.acceptedToSender"), sender, true);
                Utils.sendMessage(CONFIG.getString("commands.tpaccept.messages.accepted"), target, true);
            })
            .register();
    }
}
