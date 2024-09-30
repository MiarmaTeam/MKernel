package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.common.minecraft.TpaType;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import static dev.gallardo.miarmacore.util.Constants.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class TpaCommand {
    public static void register() {
        new CommandAPICommand(CONFIG.getString("commands.tpa.name"))
            .withArguments(PLAYER_ARG)
            .withPermission(CONFIG.getString("commands.tpa.permission"))
            .withFullDescription(CONFIG.getString("commands.tpa.description"))
            .withShortDescription(CONFIG.getString("commands.tpa.description"))
            .withUsage(CONFIG.getString("commands.tpa.usage"))
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

                TPA_REQUESTS.addRequest(sender, target, TpaType.TPA);
                LOGGER.info(TPA_REQUESTS.toString());

                Utils.sendMessage(
                        Utils.placeholderParser(
                                CONFIG.getString("language.commands.tpa.tpaToPlayer"),
                                List.of("%target%"),
                                List.of(target.getName())
                        ),
                        sender, true
                );

                Utils.sendMessage(
                        Utils.placeholderParser(
                                CONFIG.getString("language.commands.tpa.tpaFromPlayer"),
                                List.of("%sender%"),
                                List.of(sender.getName())
                        ),
                        target, true
                );
            })
            .register();
    }
}
