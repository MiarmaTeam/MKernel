package net.miarma.mkernel.commands;

import dev.jorel.commandapi.CommandAPI;
import net.miarma.mkernel.commands.admin.*;
import net.miarma.mkernel.commands.base.BaseCommand;
import net.miarma.mkernel.commands.misc.*;
import net.miarma.mkernel.commands.roleplay.DoCommand;
import net.miarma.mkernel.commands.roleplay.MeCommand;
import net.miarma.mkernel.commands.tp.TpDenyCommand;
import net.miarma.mkernel.commands.tp.TpaAcceptCommand;
import net.miarma.mkernel.commands.tp.TpaCommand;
import net.miarma.mkernel.commands.tp.TpaHereCommand;
import net.miarma.mkernel.commands.troll.LaunchCommand;
import net.miarma.mkernel.common.minecraft.VersionedRegistrar;

public class CommandManager {

    public static void onEnable() {
        CommandAPI.unregister("me");

        register(BaseCommand.class);
        register(TpaCommand.class);
        register(TpaAcceptCommand.class);
        register(TpDenyCommand.class);
        register(TpaHereCommand.class);
        register(MeCommand.class);
        register(DoCommand.class);
        register(GlobalChestCommand.class);
        register(DisposalCommand.class);
        register(OpMeCommand.class);
        register(DeOpMeCommand.class);
        register(SpawnCommand.class);
        register(LobbyCommand.class);
        register(BlockWorldCommand.class);
        register(SpecialItemCommand.class);
        register(SendCoordsCommand.class);
        register(PayXpCommand.class);
        register(HomeCommand.class);
        register(SetHomeCommand.class);
        register(InventoryRecoveryCommand.class);
        register(GmcCommand.class);
        register(GmsCommand.class);
        register(GmspCommand.class);
        register(GmaCommand.class);
        register(VanishCommand.class);
        register(WarpCommand.class);
        register(SpyCommand.class);
        register(FreezeCommand.class);
        register(HealCommand.class);
        register(LaunchCommand.class);
        register(DayCommand.class);
        register(NightCommand.class);
        register(ThunderCommand.class);
        register(SunCommand.class);
        register(RainCommand.class);
        register(InvseeCommand.class);
    }

    private static void register(Class<?> commandClass) {
        VersionedRegistrar.registerCommand(commandClass);
    }
}
