package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.BaseManager;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.Warning;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.net.InetAddress;

public final class WarningManager extends BaseManager<Warning> {
    public WarningManager(PunishmentFactory<Warning> factory, BaseStorage<Warning> storage) {
        super(factory, storage, Warning.class);
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
