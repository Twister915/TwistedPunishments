package me.twister915.punishments.model.storage;

import me.twister915.punishments.model.PunishException;

public interface ConnectionLoader<T extends DBConnection> {
    T getNewConnection() throws PunishException;
}
