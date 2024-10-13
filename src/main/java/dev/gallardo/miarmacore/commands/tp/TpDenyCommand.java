package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.common.minecraft.TpaRequest;
import dev.gallardo.miarmacore.common.minecraft.TpaRequests;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.TPA_TARGETS;
import static dev.gallardo.miarmacore.util.Constants.*;

import java.util.List;

public class TpDenyCommand {
    public static void register() {
        CommandWrapper tpDenyCmd = CommandProvider.getTpDenyCommand();
        new CommandAPICommand(tpDenyCmd.getName())
            .withArguments(TPA_TARGETS)
            .withPermission(tpDenyCmd.getPermission().base())
            .withFullDescription(tpDenyCmd.getDescription())
            .withShortDescription(tpDenyCmd.getDescription())
            .withUsage(tpDenyCmd.getUsage())
            .executesPlayer((sender, args) -> {
                Player target = Bukkit.getPlayer(args.getRaw(0));

                if (target == null || !target.isOnline()) {
                    MessageUtils.sendMessage(sender, MessageProvider.Errors.playerNotFound(), true);
                    return;
                }

                TpaRequest request;
                if(TpaRequests.getInstance().getTpaRequest(target, sender) != null) {
                    request = TpaRequests.getInstance().getTpaRequest(target, sender);
                } else {
                    request = TpaRequests.getInstance().getTpaHereRequest(target, sender);
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
            })
            .register();
    }
}
