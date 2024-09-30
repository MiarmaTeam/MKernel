package dev.gallardo.miarmacore.commands;

import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;
import static dev.gallardo.miarmacore.util.Constants.*;

public class MiarmaCoreCommand {
    public static void register() {
        new CommandAPICommand("miarmacore")
                .withAliases("mc","mcore")
                .withFullDescription(CONFIG.getString("commands.miarmacore.description"))
                .withShortDescription(CONFIG.getString("commands.miarmacore.description"))
                .executes((sender, args) -> {
                    Utils.sendMessage(
                            "[P] Desarrollado por &#2ca268&lGallardo7761&r para &#2ca268&lMiarmaCraft&r",
                            sender,
                            true
                    );
                })
                .withSubcommand(
                        new CommandAPICommand("reload")
                                .withPermission("miarmacore.reload")
                                .withFullDescription(CONFIG.getString("commands.miarmacore.reload.description"))
                                .withShortDescription(CONFIG.getString("commands.miarmacore.reload.description"))
                                .executesPlayer((player, args) -> {
                                    try {
                                        CONFIG.reload();
                                        Utils.sendMessage(CONFIG.getString("language.commands.miarmacore.reload.success"),
                                                player,true);
                                    } catch(Exception e) {
                                        Utils.sendMessage(CONFIG.getString("language.commands.miarmacore.reload.error"),
                                                player,true);
                                    }
                                })
                )
                .register();
    }
}
