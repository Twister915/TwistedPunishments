package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.model.manager.BaseManager;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.Ban;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.util.Date;

public final class BanManager extends BaseManager<Ban> {
    public BanManager(BaseStorage<Ban> storage) {
        super(storage, Ban.class);
    }

    @Override
    protected Ban createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds) {
        return new Ban(active, reason, date, punisherId, player);
    }

    @Override
    protected void onPunish(Ban punishment, OfflinePlayer punished) {
        if (punished instanceof Player) ((Player) punished).kickPlayer(getDisconnectMessage(punishment));
    }

    @Override
    protected boolean canConnect(Ban punishment, Player player, InetAddress address) {
        return false;
    }
}
