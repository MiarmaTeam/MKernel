package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.TpaRequest;
import dev.gallardo.miarmacore.common.TpaType;
import dev.gallardo.miarmacore.util.Utils;
import static dev.gallardo.miarmacore.util.Constants.*;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class TpaAcceptCommand {
    public static void register() {
        new CommandAPICommand("tpaccept")
            .withArguments(PLAYER_ARG)
            .withPermission("miarmacore.tpaccept")
            .withFullDescription(CONFIG.getString("commands.tpaccept.description"))
            .withShortDescription(CONFIG.getString("commands.tpaccept.description"))
            .withUsage(CONFIG.getString("commands.tpaccept.usage"))
            .executesPlayer((sender, args) -> {
                MiarmaCore.logger.info(TPA_REQUESTS.toString());

                if (!(sender instanceof Player)) {
                    sender.sendMessage(CONFIG.getString("language.errors.onlyPlayerCommand"));
                    return;
                }

                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    Utils.sendMessage(CONFIG.getString("language.errors.playerNotFound"), sender, true);
                    return;
                }

                // Buscar la solicitud de TPA correcta entre el sender y el target
                Optional<TpaRequest> requestOpt = TPA_REQUESTS.getRequests().stream()
                        .filter(request ->
                                request.getFrom().equals(target) && request.getTo().equals(sender)
                        )
                        .findFirst();

                if (requestOpt.isEmpty()) {
                    Utils.sendMessage(CONFIG.getString("language.errors.noRequestFound"), sender, true);
                    return;
                }

                TpaRequest request = requestOpt.get();
                TPA_REQUESTS.removeRequest(request); // Eliminar la solicitud después de aceptarla

                // Teletransportar al target según el tipo de solicitud (TPA o TPA_HERE)
                if (request.getType() == TpaType.TPA) {
                    sender.teleport(target.getLocation());
                } else if (request.getType() == TpaType.TPA_HERE) {
                    target.teleport(sender.getLocation());
                }

                Utils.sendMessage(CONFIG.getString("language.commands.tpaccept.success"), sender, true);
                Utils.sendMessage(CONFIG.getString("language.commands.tpaccept.successToSender"), target, true);
            })
            .register();
    }
}
