package me.twister915.punishments.model;

import org.bukkit.OfflinePlayer;

import java.util.Date;

public interface Punishment {
    String getReason();
    Date getDatePunished();
    boolean isActive();
    void setActive(boolean value);
    String getPunisherIdentifier();
    OfflinePlayer getPunished();
    Integer getLengthInSeconds();
    String getDBId();
    void setDBId(String id);
}
