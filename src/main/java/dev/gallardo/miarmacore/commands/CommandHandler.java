package dev.gallardo.miarmacore.commands;

import dev.gallardo.miarmacore.commands.admin.*;
import dev.gallardo.miarmacore.commands.base.MiarmaCoreCommand;
import dev.gallardo.miarmacore.commands.misc.*;
import dev.gallardo.miarmacore.commands.roleplay.DoCommand;
import dev.gallardo.miarmacore.commands.roleplay.MeCommand;
import dev.gallardo.miarmacore.commands.tp.TpDenyCommand;
import dev.gallardo.miarmacore.commands.tp.TpaAcceptCommand;
import dev.gallardo.miarmacore.commands.tp.TpaCommand;
import dev.gallardo.miarmacore.commands.tp.TpaHereCommand;
import dev.gallardo.miarmacore.commands.troll.LaunchCommand;
import dev.jorel.commandapi.CommandAPI;

public class CommandHandler {
    private static void registerCommands() {
        MiarmaCoreCommand.register();
        RegistroWebCommand.register();
        TpaCommand.register();
        TpaAcceptCommand.register();
        TpDenyCommand.register();
        TpaHereCommand.register();
        CommandAPI.unregister("me");
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
        HomeCommand.register();
        SetHomeCommand.register();
        InventoryRecoveryCommand.register();
        GmcCommand.register();
        GmsCommand.register();
        GmspCommand.register();
        GmaCommand.register();
        VanishCommand.register();
        WarpCommand.register();
        SpyCommand.register();
        FreezeCommand.register();
        HealCommand.register();
        LaunchCommand.register();
        DayCommand.register();
        NightCommand.register();
        ThunderCommand.register();
        SunCommand.register();
        RainCommand.register();
    }

    public static void onEnable() {
        registerCommands();
    }
}
