package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.BaseManager;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.Kick;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.net.InetAddress;

public final class KickManager extends BaseManager<Kick> {
    public KickManager(PunishmentFactory<Kick> factory, BaseStorage<Kick> storage) {
        super(factory, storage, Kick.class);
    }

    @Override
    protected boolean canConnect(Kick punishment, Player player, InetAddress address) {
        return true;
    }

    @Override
    protected void onPunish(Kick punishment, OfflinePlayer punished) {
        if (punished instanceof Player) ((Player) punished).kickPlayer(getDisconnectMessage(punishment));
    }
}
