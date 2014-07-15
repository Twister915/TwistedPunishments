package me.twister915.punishments.model.manager.storage;

import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.BaseStorage;

import java.sql.SQLException;

public interface DBConnection {
    <P extends Punishment> BaseStorage<P> getStorageFor(Class<P> punishmentType, PunishmentFactory<P> factory) throws PunishException;
}
