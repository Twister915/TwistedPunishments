package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.BaseManager;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.TemporaryBan;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.net.InetAddress;

public final class TemporaryBanManager extends BaseManager<TemporaryBan> {
    public TemporaryBanManager(PunishmentFactory<TemporaryBan> factory, BaseStorage<TemporaryBan> storage) {
        super(factory, storage, TemporaryBan.class);
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
