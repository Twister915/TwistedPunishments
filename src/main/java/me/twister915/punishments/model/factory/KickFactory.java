package me.twister915.punishments.model.factory;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.type.Kick;
import org.bukkit.OfflinePlayer;

import java.util.Date;

public final class KickFactory implements PunishmentFactory<Kick> {
    @Override
    public Kick createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds) {
        return new Kick(reason, date, punisherId, player);
    }
}
