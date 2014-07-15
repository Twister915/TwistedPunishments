package me.twister915.punishments.model.factory;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.PunishmentManager;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.manager.impl.BanManager;
import me.twister915.punishments.model.type.Ban;
import org.bukkit.OfflinePlayer;

import java.util.Date;

public final class BanFactory implements PunishmentFactory<Ban> {
    @Override
    public Ban createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds) {
        return new Ban(active, reason, date, punisherId, player);
    }

    @Override
    public PunishmentManager<Ban> getNewManager(BaseStorage<Ban> storage) {
        return new BanManager(this, storage);
    }
}
