package me.twister915.punishments;

import lombok.Getter;
import lombok.Value;
import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.PunishmentManager;
import me.twister915.punishments.model.factory.*;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.storage.DBConnection;
import me.twister915.punishments.model.storage.mysql.MySQLConnection;
import me.twister915.punishments.model.type.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class TwistedPunishments extends JavaPlugin {
    @Getter private static TwistedPunishments instance;

    private Set<PunishmentSystem<?>> punishments = new HashSet<>();
    private DBConnection connection;

    @Override
    public void onEnable() {
        instance = this;
        try {
            connection = new MySQLConnection("", "");
            registerPunishment(Kick.class, new KickFactory());
            registerPunishment(Warning.class, new WarningFactory());
            registerPunishment(TemporaryMute.class, new TemporaryMuteFactory());
            registerPunishment(TemporaryBan.class, new TemporaryBanFactory());
            registerPunishment(Mute.class, new MuteFactory());
            registerPunishment(Ban.class, new BanFactory());
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private <T extends Punishment> void registerPunishment(Class<T> punishment, PunishmentFactory<T> factory) throws PunishException {
        BaseStorage<T> storageFor = connection.getStorageFor(punishment, factory);
        PunishmentSystem<T> tPunishmentSystem;
        tPunishmentSystem = new PunishmentSystem<>(punishment, factory.getNewManager(storageFor), factory, storageFor);
        punishments.add(tPunishmentSystem);
    }

    public static <T extends Punishment> PunishmentManager<T> getManager(Class<T> punishmentType) {
        for (PunishmentSystem<?> punishment : instance.punishments) {
            if (punishment.getClass().equals(punishmentType)) //noinspection unchecked
                return (PunishmentManager<T>) punishment.getManager();
        }
        return null;
    }

    public static String getName(Class<? extends Punishment> punishmentClass) {
        return punishmentClass.getSimpleName();
    }

    @Value
    private static class PunishmentSystem<T extends Punishment> {
        private Class<T> punishmentType;
        private PunishmentManager<T> manager;
        private PunishmentFactory<T> factory;
        private BaseStorage<T> storage;
    }
}
