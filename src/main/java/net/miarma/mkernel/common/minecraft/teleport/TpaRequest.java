package net.miarma.mkernel.common.minecraft.teleport;

import org.bukkit.entity.Player;

public class TpaRequest {
    private final Player from;
    private final Player to;
    private final TpaType type;
    private final long timestamp;
    private boolean used;

    public TpaRequest(Player from, Player to, TpaType type) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.timestamp = System.currentTimeMillis();
        this.used = false;
    }

    public boolean isExpired(long timeoutMillis) {
        return System.currentTimeMillis() - this.timestamp > timeoutMillis;
    }

    public boolean isUsed() {
        return used;
    }

    public void markUsed() {
        this.used = true;
    }

    public Player getFrom() { return from; }
    public Player getTo() { return to; }
    public TpaType getType() { return type; }
    public long getTimestamp() { return timestamp; }
}
