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

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import me.twister915.punishments.TwistedPunishments;
import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.storage.ConnectionLoader;
import org.bukkit.configuration.ConfigurationSection;

import java.net.UnknownHostException;

public class MongoLoader implements ConnectionLoader<MongoConnection> {
    @Override
    public MongoConnection getNewConnection() throws PunishException {
        ConfigurationSection mongodb = TwistedPunishments.getInstance().getConfig().getConfigurationSection("mongodb");
        String host = mongodb.getString("host", "127.0.0.1");
        int port = mongodb.getInt("port", 27017);
        String username = mongodb.getString("username");
        String password = mongodb.getString("psasword");
        String database = mongodb.getString("database", "minecraft");
        String prefix = mongodb.getString("prefix");
        MongoClientURI uri;
        if (username == null || password == null) {
            uri = new MongoClientURI("mongodb://" + host + ":" + port + "/" + database);
        } else {
            uri = new MongoClientURI("mongodb://" + username + ":" + password + "@" + host + ":" + "/" + database);
        }
        try {
            return new MongoConnection(prefix, new MongoClient(uri).getDB(database));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new PunishException(e.getMessage());
        }
    }
}
