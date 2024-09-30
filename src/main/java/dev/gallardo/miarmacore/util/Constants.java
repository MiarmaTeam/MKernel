package dev.gallardo.miarmacore.util;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.TpaRequests;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Constants {
    public static final ConfigWrapper CONFIG = MiarmaCore.getConf();
    public static TpaRequests TPA_REQUESTS = TpaRequests.getInstance();
    public static final Argument<?> PASSWORD_ARG = new GreedyStringArgument(CONFIG.getString("arguments.password"));
    public static Argument<?> PLAYER_ARG = new PlayerArgument(CONFIG.getString("arguments.player"))
            .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName).toList().toArray(new String[Bukkit.getOnlinePlayers().size()])));
}
