package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.minecraft.DisposalInventory;
import dev.gallardo.miarmacore.common.minecraft.GlobalChest;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dev.gallardo.miarmacore.util.Constants.*;

public class DisposalCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.disposal.name"))
            .withOptionalArguments(PLAYERS_OPT_ARG.withPermission(
                    MiarmaCore.CONFIG.getString("commands.disposal.permissions.others")
            ))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.disposal.description"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.disposal.permission"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.disposal.description"))
            .executesPlayer((sender,args) -> {
                if (args.count() > 1) {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.tooManyArguments"), sender, true);
                } else if (args.count() == 0) {
                    Player player = sender;
                    player.openInventory(DisposalInventory.getInv());
                } else if (args.count() == 1) {
                    Player player = Bukkit.getServer().getPlayer(args.getRaw(0));
                    player.openInventory(DisposalInventory.getInv());
                }
            })
            .register();
    }
}
