package me.twister915.punishments.model.manager.storage.mysql;

import com.jolbox.bonecp.BoneCP;
import lombok.Data;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.manager.storage.DBConnection;

@Data
public final class MySQLConnection implements DBConnection {
    private final String url;
    private final String prefix;

    final BoneCP connectionPool;

    public <T extends Punishment> MySQLStorage<T> getStorageFor(Class<T> type) {
        return new MySQLStorage<>(type, this);
    }
}
