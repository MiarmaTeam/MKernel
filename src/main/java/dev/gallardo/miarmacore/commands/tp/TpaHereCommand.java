package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.TpaType;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYER_ARG;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.List;

public class TpaHereCommand {
    public static void register() {
        CommandWrapper tpaHereCmd = CommandProvider.getTpaHereCommand();
        new CommandAPICommand(tpaHereCmd.getName())
            .withArguments(PLAYER_ARG)
            .withPermission(tpaHereCmd.getPermission().base())
            .withFullDescription(tpaHereCmd.getDescription())
            .withShortDescription(tpaHereCmd.getDescription())
            .withUsage(tpaHereCmd.getUsage())
            .executesPlayer((sender, args) -> {
                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    MessageUtils.sendMessage(MessageProvider.Errors.playerNotFound(), sender, true);
                    return;
                }

                if (target.equals(sender)) {
                    MessageUtils.sendMessage(MessageProvider.Errors.cantTeleportToYourself(), sender, true);
                    return;
                }

                boolean requestExists = TPA_REQUESTS.getRequests().stream()
                        .anyMatch(request -> request.from().equals(sender) && request.to().equals(target));

                if (requestExists) {
                    MessageUtils.sendMessage(MessageProvider.Errors.requestAlreadySent(), sender, true);
                    return;
                }

                TPA_REQUESTS.addRequest(target, sender, TpaType.TPA_HERE);
                MiarmaCore.LOGGER.info(TPA_REQUESTS.toString());

                MessageUtils.sendMessage(tpaHereCmd.getMessages()[1], sender, true, true,
                        List.of("%target%"), List.of(target.getName()));

                MessageUtils.sendMessage(tpaHereCmd.getMessages()[0], target, true, true,
                        List.of("%sender%"), List.of(sender.getName()));
            })
            .register();
    }
}
