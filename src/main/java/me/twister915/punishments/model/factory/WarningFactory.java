package me.twister915.punishments.model.factory;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.type.Warning;
import org.bukkit.OfflinePlayer;

import java.util.Date;

public final class WarningFactory implements PunishmentFactory<Warning> {
    @Override
    public Warning createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds) {
        return new Warning(reason, date, punisherId, player);
    }
}
