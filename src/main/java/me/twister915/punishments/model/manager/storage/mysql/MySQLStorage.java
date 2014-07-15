package me.twister915.punishments.model.manager.storage.mysql;

import lombok.Data;
import me.twister915.punishments.TwistedPunishments;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.manager.BaseStorage;
import org.bukkit.OfflinePlayer;

import java.util.Set;

@Data
public final class MySQLStorage<T extends Punishment> implements BaseStorage<T> {
    private final Class<T> type;
    private final String tableName;
    private final MySQLConnection connection;

    public MySQLStorage(Class<T> type, MySQLConnection connection) {
        this.type = type;
        this.connection = connection;
        tableName = TwistedPunishments.getName(type).toLowerCase() + "s";
    }

    @Override
    public void store(T punishment) {

    }

    @Override
    public Set<T> getForPlayer(OfflinePlayer player) {
        return null;
    }
}
