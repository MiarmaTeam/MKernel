package dev.gallardo.miarmacore.common;

import dev.gallardo.miarmacore.commands.TpaType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TpaRequests {
    private List<TpaRequest> requests = new ArrayList<>();
    private static TpaRequests instance;

    private TpaRequests() {}

    public static TpaRequests getInstance() {
        if(instance == null) {
            instance = new TpaRequests();
        }
        return instance;
    }

    public void addRequest(Player from, Player to, TpaType type) {
        requests.add(new TpaRequest(from, to, type));
    }

    public TpaRequest getRequest(Player from, Player to, TpaType type) {
        return requests.stream()
                .filter(request -> request.getFrom().equals(from)
                        && request.getTo().equals(to)
                        && request.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

    public List<TpaRequest> getRequests() {
        return requests;
    }

    public void removeRequest(Player from, Player to, TpaType type) {
        requests.stream()
                .filter(request -> request.getFrom().equals(from)
                        && request.getTo().equals(to)
                        && request.getType().equals(type))
                .findFirst()
                .ifPresent(requests::remove);
    }

    public void removeRequest(TpaRequest request) {
        requests.remove(request);
    }

    public void clearRequests() {
        requests.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(TpaRequest request : requests) {
            sb.append(request.getFrom().getName())
                    .append(" -> ")
                    .append(request.getTo().getName())
                    .append(" (")
                    .append(request.getType())
                    .append(")\n");
        }
        return sb.toString();
    }
}

