package me.twister915.punishments.model.storage.mongodb;

import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.storage.ConnectionLoader;

public class MongoLoader implements ConnectionLoader<MongoConnection> {
    @Override
    public MongoConnection getNewConnection() throws PunishException {
        return null;
    }
}
