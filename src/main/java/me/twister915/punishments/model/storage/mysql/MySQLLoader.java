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

package me.twister915.punishments.model.storage.mysql;

import me.twister915.punishments.TwistedPunishments;
import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.storage.ConnectionLoader;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.SQLException;

public final class MySQLLoader implements ConnectionLoader<MySQLConnection> {
    @Override
    public MySQLConnection getNewConnection() throws PunishException {
        ConfigurationSection mysql = TwistedPunishments.getInstance().getConfig().getConfigurationSection("mysql");
        String host = mysql.getString("host", "127.0.0.1");
        int port = mysql.getInt("port", 3306);
        String username = mysql.getString("username", "root");
        String password = mysql.getString("password", "root");
        String database = mysql.getString("db", "minecraft");
        String prefix = mysql.getString("tablePrefix");
        try {
            return new MySQLConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password, prefix);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PunishException(e.getMessage());
        }
    }
}
