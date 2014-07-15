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
