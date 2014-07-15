package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.BaseManager;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.Ban;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.util.Date;

public final class BanManager extends BaseManager<Ban> {
    public BanManager(PunishmentFactory<Ban> factory, BaseStorage<Ban> storage) {
        super(factory, storage, Ban.class);
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
