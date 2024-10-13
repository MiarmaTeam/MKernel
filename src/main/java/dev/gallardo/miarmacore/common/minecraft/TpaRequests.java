package dev.gallardo.miarmacore.common.minecraft;

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
        // Check if a request already exists from the same players to avoid duplicates
        TpaRequest existingRequest = getRequest(from, to);
        if (existingRequest == null) {
            requests.add(new TpaRequest(from, to, type));
        }
    }

    public TpaRequest getRequest(Player from, Player to) {
        return requests.stream()
                .filter(request -> request.from().equals(from) && request.to().equals(to))
                .findFirst()
                .orElse(null);
    }

    public List<TpaRequest> getRequests() {
        return requests;
    }

    public void removeRequest(TpaRequest request) {
        requests.remove(request);
    }

    public void clearRequests() {
        requests.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TpaRequest request : requests) {
            sb.append(request.from().getName())
                    .append(" -> ")
                    .append(request.to().getName())
                    .append(" (")
                    .append(request.type())
                    .append(")\n");
        }
        return sb.toString();
    }
}
