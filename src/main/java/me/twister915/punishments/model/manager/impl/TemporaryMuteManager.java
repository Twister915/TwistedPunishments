package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.TemporaryMute;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;

public final class TemporaryMuteManager extends BaseMuteManager<TemporaryMute> {
    public TemporaryMuteManager(BaseStorage<TemporaryMute> storage) {
        super(storage, TemporaryMute.class);
    }

    @Override
    protected TemporaryMute createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds) {
        return new TemporaryMute(active, reason, date, punisherId, player, lengthInSeconds);
    }

    @Override
    protected void handleChatEvent(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        //TODO message
    }
}
