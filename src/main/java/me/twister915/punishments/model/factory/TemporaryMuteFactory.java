package me.twister915.punishments.model.factory;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.PunishmentManager;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.manager.impl.TemporaryMuteManager;
import me.twister915.punishments.model.type.TemporaryMute;
import org.bukkit.OfflinePlayer;

import java.util.Date;

public final class TemporaryMuteFactory implements PunishmentFactory<TemporaryMute> {
    @Override
    public TemporaryMute createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds) {
        return new TemporaryMute(active, reason, date, punisherId, player, lengthInSeconds);
    }

    @Override
    public PunishmentManager<TemporaryMute> getNewManager(BaseStorage<TemporaryMute> storage) {
        return new TemporaryMuteManager(this, storage);
    }
}
