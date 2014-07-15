package me.twister915.punishments.model.factory;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.type.Mute;
import org.bukkit.OfflinePlayer;

import java.util.Date;

public final class MuteFactory implements PunishmentFactory<Mute> {
    @Override
    public Mute createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds) {
        return new Mute(active, reason, date, punisherId, player);
    }
}
