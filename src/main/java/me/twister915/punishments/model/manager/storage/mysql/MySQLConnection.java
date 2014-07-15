package me.twister915.punishments.model.manager.storage.mysql;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import lombok.Data;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.storage.DBConnection;

import java.sql.SQLException;

@Data
public final class MySQLConnection implements DBConnection {
    private final String url;
    private final String prefix;

    final BoneCP connectionPool;

    public MySQLConnection(String url, String prefix) throws SQLException {
        this.url = url;
        this.prefix = prefix;
        connectionPool = new BoneCP(new BoneCPConfig());
    }

    public <T extends Punishment> MySQLStorage<T> getStorageFor(Class<T> type, PunishmentFactory<T> factory) throws SQLException {
        return new MySQLStorage<>(type, factory, this);
    }
}
