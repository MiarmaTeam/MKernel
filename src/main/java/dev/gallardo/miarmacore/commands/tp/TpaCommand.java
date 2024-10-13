package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.TpaRequests;
import dev.gallardo.miarmacore.common.minecraft.TpaType;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYER_ARG;

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
                        MessageUtils.sendMessage(sender, MessageProvider.Errors.playerNotFound(), true);
                        return;
                    }

                    if (target.equals(sender)) {
                        MessageUtils.sendMessage(sender, MessageProvider.Errors.cantTeleportToYourself(), true);
                        return;
                    }

                    boolean requestExists = TpaRequests.getInstance().getTpaRequest(sender, target) != null;

                    if (requestExists) {
                        MessageUtils.sendMessage(sender, MessageProvider.Errors.requestAlreadySent(), true);
                        return;
                    }

                    TpaRequests.getInstance().addRequest(sender, target, TpaType.TPA);
                    MiarmaCore.LOGGER.info(TpaRequests.getInstance().toString());

                    MessageUtils.sendMessage(sender, tpaCmd.getMessages()[1], true,
                            List.of("%target%"), List.of(target.getName()));
                    MessageUtils.sendMessage(target, tpaCmd.getMessages()[0], true,
                            List.of("%sender%"), List.of(sender.getName()));
                })
                .register();
    }
}
