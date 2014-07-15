package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.Mute;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;

public final class MuteManager extends BaseMuteManager<Mute> {
    public MuteManager(BaseStorage<Mute> storage) {
        super(storage, Mute.class);
    }

    @Override
    protected Mute createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds) {
        return new Mute(active, reason, date, punisherId, player);
    }

    @Override
    protected void handleChatEvent(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        //TODO message
    }
}
