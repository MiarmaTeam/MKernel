package dev.gallardo.miarmacore.commands.tp;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.teleport.TpaRequests;
import dev.gallardo.miarmacore.common.minecraft.teleport.TpaType;
import dev.gallardo.miarmacore.config.CommandWrapper;
import dev.gallardo.miarmacore.config.providers.CommandProvider;
import dev.gallardo.miarmacore.config.providers.MessageProvider;
import dev.gallardo.miarmacore.util.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

import static dev.gallardo.miarmacore.config.providers.CommandProvider.Arguments.PLAYER_ARG;

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
                        MessageUtil.sendMessage(sender, MessageProvider.Errors.playerNotFound(), true);
                        return;
                    }

                    if (target.equals(sender)) {
                        MessageUtil.sendMessage(sender, MessageProvider.Errors.cantTeleportToYourself(), true);
                        return;
                    }

                    boolean requestExists = TpaRequests.getInstance().getTpaHereRequest(target, sender) != null;

                    if (requestExists) {
                        MessageUtil.sendMessage(sender, MessageProvider.Errors.requestAlreadySent(), true);
                        return;
                    }

                    TpaRequests.getInstance().addRequest(target, sender, TpaType.TPA_HERE);
                    MiarmaCore.LOGGER.info(TpaRequests.getInstance().toString());

                    MessageUtil.sendMessage(sender, tpaHereCmd.getMessages()[1], true,
                            List.of("%target%"), List.of(target.getName()));
                    MessageUtil.sendMessage(target, tpaHereCmd.getMessages()[0], true,
                            List.of("%sender%"), List.of(sender.getName()));
                })
                .register();
    }
}
