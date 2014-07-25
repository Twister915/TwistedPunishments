/******************************************************************************
 Copyright 2014 Joey Sacchini                                                 *
                                                                              *
 Licensed under the Apache License, Version 2.0 (the "License");              *
 you may not use this file except in compliance with the License.             *
 You may obtain a copy of the License at                                      *
                                                                              *
     http://www.apache.org/licenses/LICENSE-2.0                               *
                                                                              *
 Unless required by applicable law or agreed to in writing, software          *
 distributed under the License is distributed on an "AS IS" BASIS,            *
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.     *
 See the License for the specific language governing permissions and          *
 limitations under the License.                                               *
 ******************************************************************************/

package me.twister915.punishments.model.storage.mongodb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import lombok.Data;
import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.storage.DBKey;
import org.bukkit.OfflinePlayer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public final class MongoStorage<T extends Punishment> implements BaseStorage<T> {
    private static final String ID_KEY = "_id";

    private final Class<T> type;
    private final PunishmentFactory<T> factory;
    private final MongoConnection connection;
    private final DBCollection collection;

    public MongoStorage(Class<T> type, PunishmentFactory<T> factory, MongoConnection connection) {
        this.type = type;
        this.factory = factory;
        this.connection = connection;
        collection = connection.database.getCollection((connection.getPrefix() == null ? "" : connection.getPrefix()) + type.getSimpleName().toLowerCase());
        if (collection.find().size() == 0) collection.createIndex(new BasicDBObject(DBKey.PUNISH_TARGET.toString(), 1));
    }

    @Override
    public void store(T punishment) throws PunishException {
        DBObject object = new BasicDBObject();
        if (punishment.getDBId() != null) object.put(ID_KEY, punishment.getDBId());
        object.put(DBKey.ACTIVE.toString(), punishment.isActive());
        object.put(DBKey.DATE_PUNISHED.toString(), punishment.getDatePunished());
        if (punishment.getLengthInSeconds() != 0 && punishment.getLengthInSeconds() != null)
            object.put(DBKey.LENGTH.toString(), punishment.getLengthInSeconds());
        object.put(DBKey.PUNISH_TARGET.toString(), punishment.getPunished().getUniqueId());
        object.put(DBKey.PUNISHER_ID.toString(), punishment.getPunisherIdentifier());
        object.put(DBKey.REASON.toString(), punishment.getReason());
        collection.save(object);
        punishment.setDBId(object.get(ID_KEY).toString());
    }

    @Override
    public void purge(Set<T> punishments) throws PunishException {
        BasicDBList objects = new BasicDBList();
        for (T punishment : punishments) {
            if (punishment.getDBId() == null) continue;
            objects.add(punishment.getDBId());
        }
        collection.remove(new BasicDBObject(ID_KEY, new BasicDBObject("$in", objects)));
    }

    @Override
    public Set<T> getForPlayer(OfflinePlayer player) throws PunishException {
        Set<T> punishments = new HashSet<>();
        for (DBObject dbObject : collection.find(new BasicDBObject(DBKey.PUNISH_TARGET.toString(), player.getUniqueId().toString()))) {
            boolean active = (boolean) dbObject.get(DBKey.ACTIVE.toString());
            String reason = (String) dbObject.get(DBKey.REASON.toString());
            Date date_issued = (Date) dbObject.get(DBKey.DATE_PUNISHED.toString());
            String punisher = (String) dbObject.get(DBKey.PUNISHER_ID.toString());
            Double length = ((Number) dbObject.get(DBKey.LENGTH.toString())).doubleValue();
            T aNew = factory.createNew(player, reason, punisher, date_issued, active, length);
            aNew.setDBId(dbObject.get(ID_KEY).toString());
            punishments.add(aNew);
        }
        return punishments;
    }
}
