/******************************************************************************
 Copyright 2014 Joey Sacchini                                                 *
                                                                              *
 Licensed under the Apache License, Version 2.0 (the "License");              *
 you may not use this file except in compliance with the License.             *
 You may obtain a copy of the License at                                      *
                                                                              *
     http://www.apache.org/licenses/LICENSE-2.0                               *
                                                                              *
 Unless required by applicable law or agreed to in writing, software          *
 distributed under the License is distributed on an "AS IS" BASIS,            *
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.     *
 See the License for the specific language governing permissions and          *
 limitations under the License.                                               *
 ******************************************************************************/

package me.twister915.punishments.model.manager;

import com.google.common.collect.ImmutableSet;
import lombok.Data;
import me.twister915.punishments.model.*;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.util.Date;
import java.util.Set;

@Data
public abstract class BaseManager<T extends Punishment> implements PunishmentManager<T> {
    protected final PunishmentFactory<T> factory;
    protected final BaseStorage<T> storage;
    protected final Class<T> type;

    protected void onPunish(T punishment, OfflinePlayer punished) {}
    protected void onUnPunish(T punishment, OfflinePlayer punished) {}
    protected abstract boolean canConnect(T punishment, Player player, InetAddress address);

    protected void onConnect(T punishment) {}

    @Override
    public T punish(OfflinePlayer player, String reason, CommandSender punisher) throws PunishException {
        return punish(player, reason, punisher, 0D);
    }

    @Override
    public T punish(OfflinePlayer player, String reason, CommandSender punisher, Double lengthInSeconds) throws PunishException {
        T punishment = factory.createNew(player, reason, (punisher instanceof Player ? ((Player) punisher).getUniqueId().toString() : "CONSOLE"), new Date(), true, lengthInSeconds);
        storage.store(punishment);
        onPunish(punishment, player);
        return punishment;
    }

    @Override
    public void unPunish(OfflinePlayer player) throws PunishException {
        T activePunishmentFor = getActivePunishmentFor(player);
        if (activePunishmentFor == null) throw new PunishException("There is no punishment for this user!");
        //noinspection deprecation
        activePunishmentFor.setActive(false);
        storage.store(activePunishmentFor);
        onUnPunish(activePunishmentFor, player);
    }

    @Override
    public T getActivePunishmentFor(OfflinePlayer player) throws PunishException {
        Set<T> forPlayer = getAllPunishmentsFor(player);
        for (T t : forPlayer) {
            if (t.isActive()) return t;
        }
        return null;
    }

    @Override
    public ImmutableSet<T> getAllPunishmentsFor(OfflinePlayer player) throws PunishException {
        return ImmutableSet.copyOf(storage.getForPlayer(player));
    }

    @Override
    public void purgePunishments(Set<T> punishments) throws PunishException {
        storage.purge(punishments);
    }

    @Override
    public void onPlayerConnect(Player player, InetAddress address) throws ConnectException {
        T activePunishmentFor;
        try {
            activePunishmentFor = getActivePunishmentFor(player);
        } catch (PunishException e) {
            if (player.isOp()) {
                player.sendMessage(ChatColor.RED + "Punishments are currently offline! " + e.getMessage());
                return;
            }
            throw new ConnectException(ChatColor.RED + "Punishments are currently offline! Please check the DB Connection");
        }
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
