package me.twister915.punishments.model.factory;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.type.TemporaryBan;
import org.bukkit.OfflinePlayer;

import java.util.Date;

public final class TemporaryBanFactory implements PunishmentFactory<TemporaryBan> {
    @Override
    public TemporaryBan createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds) {
        return new TemporaryBan(active, reason, date, punisherId, player, lengthInSeconds);
    }
}