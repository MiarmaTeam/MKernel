package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.common.minecraft.teleport.TpaRequest;
import dev.gallardo.miarmacore.common.minecraft.teleport.TpaRequests;
import dev.gallardo.miarmacore.common.minecraft.teleport.TpaType;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYERS_OPT_ARG;

import java.util.List;
import java.util.Optional;

public class TpDenyCommand {
    public static void register() {
        CommandWrapper tpDenyCmd = CommandProvider.getTpDenyCommand();
        new CommandAPICommand(tpDenyCmd.getName())
            .withOptionalArguments(PLAYERS_OPT_ARG)
            .withPermission(tpDenyCmd.getPermission().base())
            .withFullDescription(tpDenyCmd.getDescription())
            .withShortDescription(tpDenyCmd.getDescription())
            .withUsage(tpDenyCmd.getUsage())
            .executesPlayer((sender, args) -> {
                if(args.count() == 0) {
                    Optional<TpaRequest> optTpaRequest = TpaRequests.getInstance().getRequests().stream()
                            .filter(r -> r.from().equals(sender))
                            .findFirst();

                    Optional<TpaRequest> optTpaHereRequest = TpaRequests.getInstance().getRequests().stream()
                            .filter(r -> r.to().equals(sender))
                            .findFirst();

                    if(optTpaRequest.isEmpty() && optTpaHereRequest.isEmpty()) {
                        MessageUtils.sendMessage(sender, MessageProvider.Errors.noRequestFound(), true);
                        return;
                    }

                    TpaRequest request;
                    request = optTpaRequest.orElseGet(optTpaHereRequest::get);
                    TpaRequests.getInstance().removeRequest(request);

                    Player target = Bukkit.getPlayer(request.from().getName());

                    MessageUtils.sendMessage(sender, tpDenyCmd.getMessages()[0], true,
                            List.of("%target%"), List.of(target.getName()));
                    MessageUtils.sendMessage(target, tpDenyCmd.getMessages()[1], true,
                            List.of("%sender%"), List.of(sender.getName()));

                } else {
                    Player target = Bukkit.getPlayer(args.getRaw(0));

                    if (target == null || !target.isOnline()) {
                        MessageUtils.sendMessage(sender, MessageProvider.Errors.playerNotFound(), true);
                        return;
                    }

                    TpaRequest request;
                    if(TpaRequests.getInstance().getTpaRequest(target, sender) != null) {
                        request = TpaRequests.getInstance().getTpaRequest(target, sender);
                    } else {
                        request = TpaRequests.getInstance().getTpaHereRequest(sender, target);
                    }

                    if (request == null) {
                        MessageUtils.sendMessage(sender, MessageProvider.Errors.noRequestFound(), true);
                        return;
                    }

                    TpaRequests.getInstance().removeRequest(request);

                    MessageUtils.sendMessage(sender, tpDenyCmd.getMessages()[0], true,
                            List.of("%target%"), List.of(target.getName()));
                    MessageUtils.sendMessage(target, tpDenyCmd.getMessages()[1], true,
                            List.of("%sender%"), List.of(sender.getName()));
                }
            })
            .register();
    }
}
