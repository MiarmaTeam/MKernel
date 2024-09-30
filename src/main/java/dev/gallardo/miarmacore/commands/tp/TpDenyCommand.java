package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.TpaRequest;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.Optional;

public class TpDenyCommand {
    public static void register() {
        new CommandAPICommand("tpdeny")
            .withArguments(PLAYER_ARG)
            .withPermission("miarmacore.tpdeny")
            .withFullDescription(CONFIG.getString("commands.tpdeny.description"))
            .withShortDescription(CONFIG.getString("commands.tpdeny.description"))
            .withUsage(CONFIG.getString("commands.tpdeny.usage"))
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
                TPA_REQUESTS.removeRequest(request); // Eliminar la solicitud después de denegarla

                // Notificar a ambos jugadores
                Utils.sendMessage(CONFIG.getString("language.commands.tpdeny.success"), sender, true);
                Utils.sendMessage(CONFIG.getString("language.commands.tpdeny.senderDenied"), target, true);
            })
            .register();
    }
}
