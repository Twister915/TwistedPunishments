package me.twister915.punishments.model;

import org.bukkit.OfflinePlayer;

import java.util.Date;

public interface PunishmentFactory<T extends Punishment> {
    T createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds);
}
