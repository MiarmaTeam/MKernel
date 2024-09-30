package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.TpaType;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.List;

public class TpaHereCommand {
    public static void register() {
        new CommandAPICommand("tpahere")
            .withArguments(PLAYER_ARG)
            .withPermission("miarmacore.tpahere")
            .withFullDescription(CONFIG.getString("commands.tpahere.description"))
            .withShortDescription(CONFIG.getString("commands.tpahere.description"))
            .withUsage(CONFIG.getString("commands.tpahere.usage"))
            .executesPlayer((sender, args) -> {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(CONFIG.getString("language.errors.onlyPlayerCommand"));
                    return;
                }

                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    Utils.sendMessage(CONFIG.getString("language.errors.playerNotFound"), sender, true);
                    return;
                }

                if (target.equals(sender)) {
                    Utils.sendMessage(CONFIG.getString("language.errors.cantTeleportToYourself"), sender, true);
                    return;
                }

                // Verificar si ya hay una solicitud activa entre el sender y el target
                boolean requestExists = TPA_REQUESTS.getRequests().stream()
                        .anyMatch(request ->
                                (request.getFrom().equals(sender) && request.getTo().equals(target)) ||
                                        (request.getFrom().equals(target) && request.getTo().equals(sender))
                        );

                if (requestExists) {
                    Utils.sendMessage(CONFIG.getString("language.errors.requestAlreadySent"), sender, true);
                    return;
                }

                // Añadir la solicitud si no existe
                TPA_REQUESTS.addRequest(target, sender, TpaType.TPA_HERE);
                MiarmaCore.logger.info(TPA_REQUESTS.toString());

                Utils.sendMessage(
                        Utils.placeholderParser(
                                CONFIG.getString("language.commands.tpahere.tpaToPlayer"),
                                List.of("%target%"),
                                List.of(target.getName())
                        ),
                        sender, true
                );

                Utils.sendMessage(
                        Utils.placeholderParser(
                                CONFIG.getString("language.commands.tpahere.tpaFromPlayer"),
                                List.of("%sender%"),
                                List.of(sender.getName())
                        ),
                        target, true
                );
            })
            .register();
    }
}
