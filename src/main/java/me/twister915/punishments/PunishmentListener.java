package me.twister915.punishments;

import me.twister915.punishments.model.ConnectException;
import me.twister915.punishments.model.PunishmentManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public final class PunishmentListener implements Listener {
    public PunishmentListener() {
        Bukkit.getPluginManager().registerEvents(this, TwistedPunishments.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        for (PunishmentManager<?> punishmentManager : TwistedPunishments.getPunishmentManagers()) {
            try {
                punishmentManager.onPlayerConnect(event.getPlayer(), event.getAddress());
            } catch (ConnectException e) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, e.getMessage());
                return;
            }
        }
    }
}
