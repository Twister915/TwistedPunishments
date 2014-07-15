package me.twister915.punishments.model.manager.storage.mongodb;

import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.manager.storage.DBConnection;

public class MongoConnection implements DBConnection {
    @Override
    public <P extends Punishment> BaseStorage<P> getStorageFor(Class<P> punishmentType, PunishmentFactory<P> factory) throws PunishException {
        return null;
    }
}
