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
import me.twister915.punishments.model.storage.mongodb.MongoLoader;
import me.twister915.punishments.model.storage.mysql.MySQLConnection;
import me.twister915.punishments.model.storage.mysql.MySQLLoader;
import me.twister915.punishments.model.type.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class TwistedPunishments extends JavaPlugin {
    @Getter private static TwistedPunishments instance;

    private Set<PunishmentSystem<?>> punishments = new HashSet<>();
    private DBConnection connection;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        try {
            String databaseType = getConfig().getString("databaseType");
            if (databaseType.equalsIgnoreCase("mysql")) connection = new MySQLLoader().getNewConnection();
            else if (databaseType.equalsIgnoreCase("mongo")) connection = new MongoLoader().getNewConnection();
            else throw new PunishException("There is no database specified for Twisted Punishments!");

            registerPunishment(Kick.class, new KickFactory());
            registerPunishment(Warning.class, new WarningFactory());
            registerPunishment(TemporaryMute.class, new TemporaryMuteFactory());
            registerPunishment(TemporaryBan.class, new TemporaryBanFactory());
            registerPunishment(Mute.class, new MuteFactory());
            registerPunishment(Ban.class, new BanFactory());

            new PunishmentListener();
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        connection.onDisable();
        punishments.clear();
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

    public static PunishmentManager<?>[] getPunishmentManagers() {
        PunishmentManager[] punishmentManagers = new PunishmentManager[instance.punishments.size()];
        Iterator<PunishmentSystem<?>> iterator = instance.punishments.iterator();
        for (int x = 0; x < instance.punishments.size(); x++) {
            PunishmentSystem<?> next = iterator.next();
            punishmentManagers[x] = next.getManager();
        }
        return punishmentManagers;
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
