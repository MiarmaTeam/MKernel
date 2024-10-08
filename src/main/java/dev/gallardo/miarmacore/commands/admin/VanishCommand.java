package dev.gallardo.miarmacore.commands.admin;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static dev.gallardo.miarmacore.util.Constants.VANISH_KEY;

public class VanishCommand {
    public static void register() {
        new CommandAPICommand(MiarmaCore.CONFIG.getString("commands.vanish.name"))
            .withAliases("v")
            .withFullDescription(MiarmaCore.CONFIG.getString("commands.vanish.description"))
            .withShortDescription(MiarmaCore.CONFIG.getString("commands.vanish.description"))
            .withPermission(MiarmaCore.CONFIG.getString("commands.vanish.permission"))
            .executesPlayer((sender,args) -> {
                PersistentDataContainer data = sender.getPersistentDataContainer();
                data.set(VANISH_KEY, PersistentDataType.BOOLEAN, !isVanished(sender));

                if(isVanished(sender)) {
                    setVanish(sender);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.vanish.messages.vanished"),
                            sender, true);
                } else {
                    unsetVanish(sender);
                    Utils.sendMessage(MiarmaCore.CONFIG.getString("commands.vanish.messages.unvanished"),
                            sender, true);
                }
            })
            .register();
    }

    private static boolean isVanished(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        return Boolean.TRUE.equals(data.get(VANISH_KEY, PersistentDataType.BOOLEAN));
    }

    private static void setVanish(Player player) {
        player.setInvisible(true);
        player.setCanPickupItems(false);
    }

    private static void unsetVanish(Player player) {
        player.setInvisible(false);
        player.setCanPickupItems(true);
    }
}
