package dev.gallardo.miarmacore.util;

import dev.gallardo.miarmacore.MiarmaCore;
import dev.gallardo.miarmacore.common.ConfigWrapper;
import dev.gallardo.miarmacore.common.minecraft.TpaRequests;
import dev.jorel.commandapi.arguments.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Constants {
    public static final ConfigWrapper CONFIG = new ConfigWrapper();

    public static final Logger LOGGER = MiarmaCore.PLUGIN.getLogger();

    public static TpaRequests TPA_REQUESTS = TpaRequests.getInstance();

    public static final Argument<?> PASSWORD_ARG = new GreedyStringArgument(CONFIG.getString("arguments.password"));

    public static Argument<?> PLAYER_ARG = new PlayerArgument(CONFIG.getString("arguments.player"))
            .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName).toList().toArray(new String[Bukkit.getOnlinePlayers().size()])));

    public static List<ItemStack> RECIPES = new ArrayList<>();

    public static String PREFIX = Utils.colorCodeParser(CONFIG.getString("language.prefix"));

    public static Argument<?> PLAYERS_ARG = new PlayerArgument(CONFIG.getString("language.player"))
            .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getOnlinePlayers().stream()
                    .map(x -> x.getName()).toList().toArray(new String[Bukkit.getOnlinePlayers().size()])));

    public static Argument<?> PLAYERS_OPT_ARG = new PlayerArgument(CONFIG.getString("language.player"))
            .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getOnlinePlayers().stream()
                    .map(x -> x.getName()).toList().toArray(new String[Bukkit.getOnlinePlayers().size()])));

    public static Argument<?> LEVELS = new IntegerArgument(CONFIG.getString("language.levels"));

    public static Argument<?> WORLDS = new StringArgument(CONFIG.getString("language.world"))
            .replaceSuggestions(ArgumentSuggestions.strings(info -> Bukkit.getWorlds().stream().map(x -> x.getName())
                    .toList().toArray(new String[Bukkit.getWorlds().size()])));

    public static Argument<?> MESSAGE = new GreedyStringArgument(CONFIG.getString("language.message"));

    public static Argument<?> ITEMS = new StringArgument(CONFIG.getString("language.item"))
            .replaceSuggestions(ArgumentSuggestions.strings(info ->
                    RECIPES.stream()
                            .map(Utils::getKey)
                            .toList().toArray(new String[RECIPES.size()])
            ));
}
