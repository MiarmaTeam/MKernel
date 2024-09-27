package dev.gallardo.miarmacore.common;

import dev.gallardo.miarmacore.commands.TpaType;
import org.bukkit.entity.Player;

public class TpaRequest {
    private Player from;
    private Player to;
    private TpaType type;

    public TpaRequest(Player from, Player to, TpaType type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }

    public TpaType getType() {
        return type;
    }
}

