package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.model.manager.BaseManager;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.TemporaryBan;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.util.Date;

public final class TemporaryBanManager extends BaseManager<TemporaryBan> {
    public TemporaryBanManager(BaseStorage<TemporaryBan> storage) {
        super(storage, TemporaryBan.class);
    }

    @Override
    protected TemporaryBan createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds) {
        return new TemporaryBan(active, reason, date, punisherId, player, lengthInSeconds);
    }

    @Override
    protected void onPunish(TemporaryBan punishment, OfflinePlayer punished) {
        if (punished instanceof Player) ((Player) punished).kickPlayer(getDisconnectMessage(punishment));
    }

    @Override
    protected boolean canConnect(TemporaryBan punishment, Player player, InetAddress address) {
        return false;
    }
}
