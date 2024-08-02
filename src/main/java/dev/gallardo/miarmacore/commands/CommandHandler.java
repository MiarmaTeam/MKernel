package dev.gallardo.miarmacore.commands;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.util.ConfigWrapper;
import dev.gallardo.miarmacore.util.Utils;
import dev.gallardo.miarmacore.util.WebAPIAccessor;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.GreedyStringArgument;

import java.util.Arrays;
import java.util.stream.Collectors;


public class CommandHandler {

    private static final ConfigWrapper cfg = MiarmaCore.getConf();
    private static final Argument<?> password = new GreedyStringArgument(cfg.getString("commands.register.arguments.password"));

    private static void registerCommands() {
        new CommandAPICommand("miarmacore")
        .withAliases("mc","mcore")
        .withFullDescription(cfg.getString("commands.miarmacore.description"))
        .withShortDescription(cfg.getString("commands.miarmacore.description"))
        .executes((sender, args) -> {
            Utils.sendMessage(
        "Desarrollado por &#006533&lGallardo7761&r para &#006533&lMiarmaCraft&r",
                sender,
          true
            );
        })
        .withSubcommand(
            new CommandAPICommand("reload")
                .withPermission("miarmacore.reload")
                .withFullDescription(cfg.getString("commands.miarmacore.reload.description"))
                .withShortDescription(cfg.getString("commands.miarmacore.reload.description"))
                .executesPlayer((player, args) -> {
                    try {
                        cfg.reload();
                        Utils.sendMessage(cfg.getString("language.commands.miarmacore.reload.success"),
                                player,true);
                    } catch(Exception e) {
                        Utils.sendMessage(cfg.getString("language.commands.miarmacore.reload.error"),
                                player,true);
                    }
                })
        )
        .register();

        new CommandAPICommand("registroweb")
        .withArguments(password)
        .withFullDescription(cfg.getString("language.galleryDescription"))
        .withPermission("miarmacore.register")
        .withShortDescription(cfg.getString("language.galleryDescription"))
        .executesPlayer((sender,args) -> {
            String password = Arrays.stream(args.rawArgs()).collect(Collectors.joining(" "));
            String username = sender.getName();
            String rol = sender.hasPermission("group.admin") ? "admin" : "user";
            if(WebAPIAccessor.register(username,password,rol)) {
                Utils.sendMessage(cfg.getString("language.messages.register.success"),
                        sender,true);
            } else {
                Utils.sendMessage(cfg.getString("language.messages.register.error"),
                        sender,true);
            }
        })
        .register();
    }

    public static void onEnable() {
        registerCommands();
    }
}
