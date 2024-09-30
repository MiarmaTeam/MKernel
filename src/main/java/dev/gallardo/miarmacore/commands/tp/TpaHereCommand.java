package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.TpaType;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.List;

public class TpaHereCommand {
    public static void register() {
        new CommandAPICommand(CONFIG.getString("commands.tpahere.name"))
            .withArguments(PLAYER_ARG)
            .withPermission(CONFIG.getString("commands.tpahere.permission"))
            .withFullDescription(CONFIG.getString("commands.tpahere.description"))
            .withShortDescription(CONFIG.getString("commands.tpahere.description"))
            .withUsage(CONFIG.getString("commands.tpahere.usage"))
            .executesPlayer((sender, args) -> {
                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    Utils.sendMessage(CONFIG.getString("language.errors.playerNotFound"), sender, true);
                    return;
                }

                if (target.equals(sender)) {
                    Utils.sendMessage(CONFIG.getString("language.errors.cantTeleportToYourself"), sender, true);
                    return;
                }

                boolean requestExists = TPA_REQUESTS.getRequests().stream()
                        .anyMatch(request ->
                                (request.from().equals(sender) && request.to().equals(target)) ||
                                        (request.from().equals(target) && request.to().equals(sender))
                        );

                if (requestExists) {
                    Utils.sendMessage(CONFIG.getString("language.errors.requestAlreadySent"), sender, true);
                    return;
                }

                TPA_REQUESTS.addRequest(target, sender, TpaType.TPA_HERE);
                LOGGER.info(TPA_REQUESTS.toString());

                Utils.sendMessage(
                        Utils.placeholderParser(
                                CONFIG.getString("commands.tpahere.messages.tpaToPlayer"),
                                List.of("%target%"),
                                List.of(target.getName())
                        ),
                        sender, true
                );

                Utils.sendMessage(
                        Utils.placeholderParser(
                                CONFIG.getString("commands.tpahere.messages.tpaFromPlayer"),
                                List.of("%sender%"),
                                List.of(sender.getName())
                        ),
                        target, true
                );
            })
            .register();
    }
}
