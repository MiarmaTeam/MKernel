package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.TpaRequest;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.Optional;

public class TpDenyCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.tpdeny.name"))
            .withArguments(PLAYER_ARG)
            .withPermission(MiarmaCore.CONFIG.getString("commands.tpdeny.permission"))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.tpdeny.description"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.tpdeny.description"))
            .withUsage(MiarmaCore.CONFIG.getString("commands.tpdeny.usage"))
            .executesPlayer((sender, args) -> {
                MiarmaCore.LOGGER.info(TPA_REQUESTS.toString());

                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.playerNotFound"), sender, true);
                    return;
                }

                // Buscar la solicitud de TPA correcta entre el sender y el target
                Optional<TpaRequest> requestOpt = TPA_REQUESTS.getRequests().stream()
                        .filter(request ->
                                request.from().equals(target) && request.to().equals(sender)
                        )
                        .findFirst();

                if (requestOpt.isEmpty()) {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.noRequestFound"), sender, true);
                    return;
                }

                TpaRequest request = requestOpt.get();
                TPA_REQUESTS.removeRequest(request); // Eliminar la solicitud después de denegarla

                // Notificar a ambos jugadores
                Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.tpdeny.messages.denied"), sender, true);
                Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.tpdeny.messages.deniedToSender"), target, true);
            })
            .register();
    }
}
