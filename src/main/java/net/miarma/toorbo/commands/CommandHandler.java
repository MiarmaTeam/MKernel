package net.miarma.toorbo.commands;

import net.miarma.toorbo.commands.admin.*;
import net.miarma.toorbo.commands.base.BaseCommand;
import net.miarma.toorbo.commands.misc.*;
import net.miarma.toorbo.commands.roleplay.DoCommand;
import net.miarma.toorbo.commands.roleplay.MeCommand;
import net.miarma.toorbo.commands.tp.TpDenyCommand;
import net.miarma.toorbo.commands.tp.TpaAcceptCommand;
import net.miarma.toorbo.commands.tp.TpaCommand;
import net.miarma.toorbo.commands.tp.TpaHereCommand;
import net.miarma.toorbo.commands.troll.LaunchCommand;
import dev.jorel.commandapi.CommandAPI;

public class CommandHandler {
    private static void registerCommands() {
        BaseCommand.register();
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
