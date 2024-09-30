package dev.gallardo.miarmacore.commands.misc;

import dev.gallardo.miarmacore.common.minecraft.GlobalChest;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import dev.gallardo.miarmacore.MiarmaCore;

import static dev.gallardo.miarmacore.util.Constants.*;

public class GlobalChestCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.globalchest.name"))
            .withOptionalArguments(PLAYERS_OPT_ARG.withPermission(
                    MiarmaCore.CONFIG.getString("commands.globalchest.permissions.others")
            ))
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.globalchest.description"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.globalchest.permissions.base"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.globalchest.description"))
            .executesPlayer((sender, args) -> {
                if (args.count() > 1) {
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("language.errors.tooManyArguments"), sender, true);
                } else if (args.count() == 0) {
                    Player player = sender;
                    player.openInventory(GlobalChest.getInv());
                } else if (args.count() == 1) {
                    Player player = Bukkit.getServer().getPlayer(args.getRaw(0));
                    player.openInventory(GlobalChest.getInv());
                }
            })
            .register();
    }
}
