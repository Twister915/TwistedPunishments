package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.Mute;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class MuteManager extends BaseMuteManager<Mute> {
    public MuteManager(PunishmentFactory<Mute> factory, BaseStorage<Mute> storage) {
        super(factory, storage, Mute.class);
    }

    @Override
    protected void handleChatEvent(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        //TODO message
    }
}
