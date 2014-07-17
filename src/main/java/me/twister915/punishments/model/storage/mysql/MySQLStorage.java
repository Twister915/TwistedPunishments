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

import com.google.common.base.Joiner;
import lombok.Data;
import me.twister915.punishments.TwistedPunishments;
import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.storage.DBKey;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Data
public final class MySQLStorage<T extends Punishment> implements BaseStorage<T> {
    private final Class<T> type;
    private final PunishmentFactory<T> factory;
    private final String tableName;
    private final MySQLConnection connection;

    public MySQLStorage(Class<T> type, PunishmentFactory<T> factory, MySQLConnection connection) throws SQLException {
        this.type = type;
        this.connection = connection;
        this.factory = factory;
        tableName = (connection.getPrefix() == null ? "" : connection.getPrefix()) + TwistedPunishments.getName(type).toLowerCase() + "s";

        //Create the table
        Connection connection1 = connection.connectionPool.getConnection();
        if (connection1.getMetaData().getTables(null, null, tableName, null).next()) return; //Check if the table exists
        Statement statement = connection1.createStatement();
        try {
            statement.executeUpdate("CREATE TABLE "
                + tableName
                + " ("
                + DBKey.ID
                + " INTEGER NULL AUTO_INCREMENT DEFAULT NULL, "
                + DBKey.PUNISH_TARGET
                + " MEDIUMTEXT NOT NULL, "
                + DBKey.DATE_PUNISHED
                + " DATE NOT NULL, "
                + DBKey.REASON
                + " MEDIUMTEXT NOT NULL, "
                + DBKey.ACTIVE
                + " BOOL NOT NULL DEFAULT 1, "
                + DBKey.LENGTH
                + " BIGINT NULL DEFAULT 0, "
                + DBKey.PUNISHER_ID
                + " MEDIUMTEXT NOT NULL, KEY("
                + DBKey.ID
                + ", "
                + DBKey.PUNISH_TARGET
                + "))");
        }
        finally {
            connection1.close();
        }
    }

    @Override
    public void store(T punishment) throws PunishException {
        DBKey[] dbKeys = {DBKey.PUNISH_TARGET, DBKey.DATE_PUNISHED, DBKey.REASON, DBKey.ACTIVE, DBKey.LENGTH, DBKey.PUNISHER_ID};
        String[] questionMarks = new String[dbKeys.length];
        Arrays.fill(questionMarks, "?");
        Connection connection1 = null;
        try {
            connection1 = connection.connectionPool.getConnection();
            if (punishment.getDBId() == null) {
                    PreparedStatement preparedStatement = connection1.prepareStatement("INSERT INTO "
                            + tableName
                            + "()"
                            + Joiner.on(", ").join(dbKeys)
                            + " VALUES ("
                            + Joiner.on(", ").join(questionMarks));
                    preparedStatement.setString(1, punishment.getPunished().getUniqueId().toString());
                    preparedStatement.setDate(2, new Date(punishment.getDatePunished().getTime()));
                    preparedStatement.setString(3, punishment.getReason());
                    preparedStatement.setBoolean(4, punishment.isActive());
                    preparedStatement.setInt(5, punishment.getLengthInSeconds());
                    preparedStatement.setString(6, punishment.getPunisherIdentifier());
                    preparedStatement.executeUpdate();
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    while (generatedKeys.next()) {
                        //noinspection deprecation
                        punishment.setDBId(String.valueOf(generatedKeys.getInt(DBKey.ID.toString())));
                    }
            }
            else {
                String[] equals = new String[dbKeys.length];
                for (int x = 0; x < dbKeys.length; x++) {
                    equals[x] = dbKeys[x].toString() + " = ?";
                }
                PreparedStatement preparedStatement = connection1.prepareStatement("UPDATE "
                        + tableName
                        + " SET "
                        + Joiner.on(", ").join(equals)
                        + " WHERE "
                        + DBKey.ID
                        + " = "
                        + punishment.getDBId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PunishException(e.getMessage());
        } finally {
            if (connection1 != null) try {
                connection1.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new PunishException(e.getMessage());
            }
        }
    }

    @Override
    public void purge(Set<T> punishments) throws PunishException {
        String[] ids = new String[punishments.size()];
        Iterator<T> iterator = punishments.iterator();
        for (int i = 0; i < punishments.size(); i++) {
            String dbId = iterator.next().getDBId();
            if (dbId == null) continue;
            ids[i] = dbId;
        }
        try {
            Statement statement = connection.connectionPool.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM " + tableName + " WHERE " + DBKey.ID + " in (" + Joiner.on(',').join(ids) + ")");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PunishException(e.getMessage());
        }
    }

    @Override
    public Set<T> getForPlayer(OfflinePlayer player) throws PunishException {
        Connection connection1;
        try {
            connection1 = connection.connectionPool.getConnection();
            Statement statement = connection1.createStatement();
            statement.execute("SELECT * FROM "
                    + tableName
                    + " WHERE "
                    + DBKey.PUNISH_TARGET
                    + " = '"
                    + player.getUniqueId()
                    + "'");
            ResultSet resultSet = statement.getResultSet();
            Set<T> results = new HashSet<>();
            while (resultSet.next()) {
                T aNew = factory.createNew(
                        Bukkit.getOfflinePlayer(UUID.fromString(resultSet.getString(DBKey.PUNISH_TARGET.toString()))),
                        resultSet.getString(DBKey.REASON.toString()),
                        resultSet.getString(DBKey.PUNISHER_ID.toString()),
                        resultSet.getDate(DBKey.DATE_PUNISHED.toString()),
                        resultSet.getBoolean(DBKey.ACTIVE.toString()),
                        resultSet.getInt(DBKey.LENGTH.toString())
                );
                //noinspection deprecation
                aNew.setDBId(String.valueOf(resultSet.getInt(DBKey.ID.toString())));
                results.add(aNew);
            }
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PunishException(e.getMessage());
        }
    }
}
