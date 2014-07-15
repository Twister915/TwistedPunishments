package me.twister915.punishments.model.manager;

import com.google.common.collect.ImmutableSet;
import lombok.Data;
import me.twister915.punishments.model.ConnectException;
import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.PunishmentManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.util.Date;
import java.util.Set;

@Data
public abstract class BaseManager<T extends Punishment> implements PunishmentManager<T> {
    protected final BaseStorage<T> storage;
    protected final Class<T> type;

    protected abstract T createNew(OfflinePlayer player, String reason, String punisherId, Date date, boolean active, Integer lengthInSeconds);
    protected void onPunish(T punishment, OfflinePlayer punished) {}
    protected void onUnPunish(T punishment, OfflinePlayer punished) {}
    protected abstract boolean canConnect(T punishment, Player player, InetAddress address);

    protected void onConnect(T punishment) {}

    @Override
    public void punish(OfflinePlayer player, String reason, CommandSender punisher) {
        punish(player, reason, punisher, 0);
    }

    @Override
    public void punish(OfflinePlayer player, String reason, CommandSender punisher, Integer lengthInSeconds) {
        T punishment = createNew(player, reason, (punisher instanceof Player ? ((Player) punisher).getUniqueId().toString() : "CONSOLE"), new Date(), true, lengthInSeconds);
        storage.store(punishment);
        onPunish(punishment, player);
    }

    @Override
    public void unPunish(OfflinePlayer player) throws PunishException {
        T activePunishmentFor = getActivePunishmentFor(player);
        if (activePunishmentFor == null) throw new PunishException("There is no punishment for this user!");
        activePunishmentFor.setActive(false);
        storage.store(activePunishmentFor);
        onUnPunish(activePunishmentFor, player);
    }

    @Override
    public T getActivePunishmentFor(OfflinePlayer player) {
        Set<T> forPlayer = getAllPunishmentsFor(player);
        for (T t : forPlayer) {
            if (t.isActive()) return t;
        }
        return null;
    }

    @Override
    public ImmutableSet<T> getAllPunishmentsFor(OfflinePlayer player) {
        return ImmutableSet.copyOf(storage.getForPlayer(player));
    }

    @Override
    public void onPlayerConnect(Player player, InetAddress address) throws ConnectException {
        T activePunishmentFor = getActivePunishmentFor(player);
        if (activePunishmentFor == null) return;
        if (canConnect(activePunishmentFor, player, address)) {
            onConnect(activePunishmentFor);
            return;
        }
        throw new ConnectException(getDisconnectMessage(activePunishmentFor));
    }

    protected String getDisconnectMessage(T punishment) {
        return "Punished! " + punishment.getReason();
    }
}
