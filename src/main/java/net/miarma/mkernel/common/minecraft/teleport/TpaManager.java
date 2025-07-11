package net.miarma.mkernel.common.minecraft.teleport;

import net.miarma.mkernel.MKernel;
import net.miarma.mkernel.config.providers.MessageProvider;
import net.miarma.mkernel.util.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TpaManager {
    private static final Map<UUID, TpaRequest> pendingRequests = new HashMap<>();
    private static TpaManager INSTANCE;

    public static TpaManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TpaManager();
        }
        return INSTANCE;
    }

    public void sendRequest(Player from, Player to, TpaType type) {
        TpaRequest req = new TpaRequest(from, to, type);
        pendingRequests.put(to.getUniqueId(), req);
    }

    public Optional<TpaRequest> getRequest(Player to) {
        TpaRequest req = pendingRequests.get(to.getUniqueId());
        if (req != null && !req.isExpired(60000) && !req.isUsed()) {
            return Optional.of(req);
        } else {
            pendingRequests.remove(to.getUniqueId());
            return Optional.empty();
        }
    }

    public boolean acceptRequest(Player to) {
        Optional<TpaRequest> optReq = getRequest(to);
        if (optReq.isPresent()) {
            TpaRequest req = optReq.get();
            req.markUsed();
            return true;
        }
        return false;
    }

    public void clearRequest(Player to) {
        pendingRequests.remove(to.getUniqueId());
    }

    public static void startAutoClearTask() {
        new BukkitRunnable() {
            public void run() {
                pendingRequests.entrySet().stream()
                    .filter(e ->
                        e.getValue().isExpired(60000) || e.getValue().isUsed()
                    )
                    .forEach(e -> {
                       pendingRequests.remove(e.getKey());
                       MessageUtil.sendMessage(e.getValue().getFrom(), MessageProvider.Misc.getTpaExpireMessage(), true);
                    });
            }
        }.runTaskTimer(MKernel.PLUGIN, 0L, 20L * 30); // 30s
    }
}
