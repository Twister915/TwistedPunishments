package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.TemporaryMute;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class TemporaryMuteManager extends BaseMuteManager<TemporaryMute> {
    public TemporaryMuteManager(PunishmentFactory<TemporaryMute> factory, BaseStorage<TemporaryMute> storage) {
        super(factory, storage, TemporaryMute.class);
    }

    @Override
    protected void handleChatEvent(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        //TODO message
    }
}
