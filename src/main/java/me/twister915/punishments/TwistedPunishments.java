package me.twister915.punishments;

import lombok.Getter;
import lombok.Value;
import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.factory.*;
import me.twister915.punishments.model.manager.BaseManager;
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.manager.impl.*;
import me.twister915.punishments.model.manager.storage.DBConnection;
import me.twister915.punishments.model.manager.storage.mysql.MySQLConnection;
import me.twister915.punishments.model.manager.storage.mysql.MySQLStorage;
import me.twister915.punishments.model.type.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
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
            registerPunishment(Kick.class, new KickFactory(), KickManager.class);
            registerPunishment(Warning.class, new WarningFactory(), WarningManager.class);
            registerPunishment(TemporaryMute.class, new TemporaryMuteFactory(), TemporaryMuteManager.class);
            registerPunishment(TemporaryBan.class, new TemporaryBanFactory(), TemporaryBanManager.class);
            registerPunishment(Mute.class, new MuteFactory(), MuteManager.class);
            registerPunishment(Ban.class, new BanFactory(), BanManager.class);
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private <T extends Punishment> void registerPunishment(Class<T> punishment, PunishmentFactory<T> factory, Class<? extends BaseManager<T>> managerClass) throws PunishException {
        BaseStorage<T> storageFor = connection.getStorageFor(punishment, factory);
        //I hate to use reflection for this :(
        Constructor<? extends BaseManager<T>> constructor;
        try {
            constructor = managerClass.getConstructor(PunishmentFactory.class, BaseStorage.class);
        } catch (NoSuchMethodException e) {
            throw new PunishException("Could not register " + getName(punishment) + " invalid constructor in manager!");
        }
        PunishmentSystem<T> tPunishmentSystem;
        try {
            tPunishmentSystem = new PunishmentSystem<>(punishment, constructor.newInstance(factory, storageFor), factory, storageFor);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new PunishException("Could not register " + getName(punishment) + " invalid constructor in manager!");
        }
        punishments.add(tPunishmentSystem);
    }

    public static String getName(Class<? extends Punishment> punishmentClass) {
        return punishmentClass.getSimpleName();
    }

    @Value
    private static class PunishmentSystem<T extends Punishment> {
        private Class<T> punishmentType;
        private BaseManager<T> manager;
        private PunishmentFactory<T> factory;
        private BaseStorage<T> storage;
    }
}
