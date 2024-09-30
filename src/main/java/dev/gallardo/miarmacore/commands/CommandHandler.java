package dev.gallardo.miarmacore.commands;

import dev.gallardo.miarmacore.commands.tp.TpaCommand;
import dev.gallardo.miarmacore.commands.tp.TpaHereCommand;
import dev.gallardo.miarmacore.commands.tp.TpaAcceptCommand;
import dev.gallardo.miarmacore.commands.tp.TpDenyCommand;

public class CommandHandler {
    private static void registerCommands() {
        MiarmaCoreCommand.register();
        RegistroWebCommand.register();
        TpaCommand.register();
        TpaAcceptCommand.register();
        TpDenyCommand.register();
        TpaHereCommand.register();
    }

    public static void onEnable() {
        registerCommands();
    }
}
