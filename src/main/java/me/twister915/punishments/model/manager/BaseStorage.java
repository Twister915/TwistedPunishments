package me.twister915.punishments.model.manager;

import me.twister915.punishments.model.Punishment;
import org.bukkit.OfflinePlayer;

import java.util.Set;

public interface BaseStorage<T extends Punishment> {
    void store(T punishment);
    Set<T> getForPlayer(OfflinePlayer player);
}
