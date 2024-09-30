package dev.gallardo.miarmacore.common.minecraft;

import org.bukkit.entity.Player;

public record TpaRequest(Player from, Player to, TpaType type) {
}

