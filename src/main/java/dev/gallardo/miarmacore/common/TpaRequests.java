package dev.gallardo.miarmacore.common;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TpaRequests {
    private List<TpaRequest> requests = new ArrayList<>();
    private static TpaRequests instance;

    private TpaRequests() {

    }

    public static TpaRequests getInstance() {
        if(instance == null) {
            instance = new TpaRequests();
        }
        return instance;
    }

    public void addRequest(Player from, Player to) {
        requests.add(new TpaRequest(from, to));
    }

    public TpaRequest getRequest(Player from, Player to) {
        return requests.stream()
            .filter(request -> request.getFrom().equals(from) && request.getTo().equals(to))
            .findFirst()
            .orElse(null);
    }

    public void removeRequest(Player from, Player to) {
        requests.stream()
                .filter(request -> request.getFrom().equals(from) && request.getTo().equals(to))
                .findFirst()
                .ifPresent(requests::remove);
    }

    public void clearRequests() {
        requests.clear();
    }

}
