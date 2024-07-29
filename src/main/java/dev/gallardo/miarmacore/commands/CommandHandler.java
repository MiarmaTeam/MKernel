package dev.gallardo.miarmacore.commands;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.ConfigWrapper;
import dev.gallardo.miarmacore.util.Utils;
import dev.jorel.commandapi.CommandAPICommand;


public class CommandHandler {

    private static final ConfigWrapper cfg = MiarmaCore.getConf();

    private static void registerCommands() {
        new CommandAPICommand("miarmacore")
                .withAliases("mc","mcore")
                .withFullDescription(cfg.getString("commands.miarmacore.description"))
                .withShortDescription(cfg.getString("commands.miarmacore.description"))
                .executes((sender, args) -> {
                    sender.sendMessage(Utils.colorCodeParser("Desarrollado por &#006533&lGallardo7761&r para &#006533&lMiarmaCraft&r"));
                })
                .withSubcommand(
                    new CommandAPICommand("reload")
                        .withPermission("miarmacore.reload")
                        .withFullDescription(cfg.getString("commands.miarmacore.reload.description"))
                        .withShortDescription(cfg.getString("commands.miarmacore.reload.description"))
                        .executesPlayer((player, args) -> {
                            try {
                                cfg.reload();
                                player.sendMessage(Utils.colorCodeParser(cfg.getString("language.commands.miarmacore.reload.success")));
                            } catch(Exception e) {
                                player.sendMessage(Utils.colorCodeParser(cfg.getString("language.commands.miarmacore.reload.error")));
                            }
                        })
                )
                .register();

    }

    public static void onEnable() {
        registerCommands();
    }
}
