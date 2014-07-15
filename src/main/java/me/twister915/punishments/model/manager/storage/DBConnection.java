package me.twister915.punishments.model.manager.storage;

import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.manager.BaseStorage;

public interface DBConnection {
    <P extends Punishment> BaseStorage<P> getStorageFor(Class<P> punishmentType);
}
