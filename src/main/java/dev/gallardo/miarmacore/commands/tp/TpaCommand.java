package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.TpaType;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYER_ARG;
import static dev.gallardo.miarmacore.util.Constants.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class TpaCommand {
    public static void register() {
        CommandWrapper tpaCmd = CommandProvider.getTpaCommand();
        new CommandAPICommand(tpaCmd.getName())
            .withArguments(PLAYER_ARG)
            .withPermission(tpaCmd.getPermission().base())
            .withFullDescription(tpaCmd.getDescription())
            .withShortDescription(tpaCmd.getDescription())
            .withUsage(tpaCmd.getUsage())
            .executesPlayer((sender, args) -> {
                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    Utils.sendMessage(MessageProvider.Errors.playerNotFound(), sender, true);
                    return;
                }

                if (target.equals(sender)) {
                    Utils.sendMessage(MessageProvider.Errors.cantTeleportToYourself(), sender, true);
                    return;
                }

                boolean requestExists = TPA_REQUESTS.getRequests().stream()
                        .anyMatch(request -> request.from().equals(sender) && request.to().equals(target));

                if (requestExists) {
                    Utils.sendMessage(MessageProvider.Errors.requestAlreadySent(), sender, true);
                    return;
                }

                TPA_REQUESTS.addRequest(sender, target, TpaType.TPA);
                MiarmaCore.LOGGER.info(TPA_REQUESTS.toString());

                Utils.sendMessage(tpaCmd.getMessages()[1], sender, true, true,
                        List.of("%target%"), List.of(target.getName())
                );

                Utils.sendMessage(tpaCmd.getMessages()[0], target, true, true,
                        List.of("%sender%"), List.of(sender.getName())
                );
            })
            .register();
    }
}
