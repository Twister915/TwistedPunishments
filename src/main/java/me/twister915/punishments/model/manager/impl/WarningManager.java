package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.model.manager.BaseManager;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.Warning;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.util.Date;

public final class WarningManager extends BaseManager<Warning> {
    public WarningManager(BaseStorage<Warning> storage) {
        super(storage, Warning.class);
    }

    @Override
    protected Warning createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds) {
        return new Warning(reason, date, punisherId, player);
    }

    @Override
    protected boolean canConnect(Warning punishment, Player player, InetAddress address) {
        return true;
    }

    @Override
    protected void onPunish(Warning punishment, OfflinePlayer punished) {
        //TODO message
    }
}
