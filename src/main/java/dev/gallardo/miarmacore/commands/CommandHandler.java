package dev.gallardo.miarmacore.commands;

import dev.gallardo.miarmacore.commands.admin.DeOpMeCommand;
import dev.gallardo.miarmacore.commands.admin.OpMeCommand;
import dev.gallardo.miarmacore.commands.base.MiarmaCoreCommand;
import dev.gallardo.miarmacore.commands.misc.*;
import dev.gallardo.miarmacore.commands.roleplay.DoCommand;
import dev.gallardo.miarmacore.commands.roleplay.MeCommand;
import dev.gallardo.miarmacore.commands.tp.TpaCommand;
import dev.gallardo.miarmacore.commands.tp.TpaHereCommand;
import dev.gallardo.miarmacore.commands.tp.TpaAcceptCommand;
import dev.gallardo.miarmacore.commands.tp.TpDenyCommand;

public class CommandHandler {
    private static void registerCommands() {
        MiarmaCoreCommand.register();
        RegistroWebCommand.register();
        //TpaCommand.register();
        //TpaAcceptCommand.register();
        //TpDenyCommand.register();
        //TpaHereCommand.register();
        MeCommand.register();
        DoCommand.register();
        GlobalChestCommand.register();
        DisposalCommand.register();
        OpMeCommand.register();
        DeOpMeCommand.register();
        SpawnCommand.register();
        LobbyCommand.register();
        BlockWorldCommand.register();
        SpecialItemCommand.register();
        SendCoordsCommand.register();
        PayXpCommand.register();
    }

    public static void onEnable() {
        registerCommands();
    }
}
