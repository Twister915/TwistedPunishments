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

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import lombok.Data;
import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.storage.DBConnection;

import java.sql.SQLException;

@Data
public final class MySQLConnection implements DBConnection {
    private final String url;
    private final String prefix;

    final BoneCP connectionPool;

    public MySQLConnection(String url, String username, String password, String prefix) throws SQLException {
        this.url = url;
        this.prefix = prefix;
        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        connectionPool = new BoneCP(config);
    }

    public <T extends Punishment> MySQLStorage<T> getStorageFor(Class<T> type, PunishmentFactory<T> factory) throws PunishException {
        try {
            return new MySQLStorage<>(type, factory, this);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PunishException(e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        connectionPool.shutdown();
    }
}
