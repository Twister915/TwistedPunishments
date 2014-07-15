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

package me.twister915.punishments.model.manager.impl;

import me.twister915.punishments.TwistedPunishments;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.PunishmentFactory;
import me.twister915.punishments.model.manager.BaseManager;
import me.twister915.punishments.model.manager.BaseStorage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseMuteManager<T extends Punishment> extends BaseManager<T>  implements Listener {
    private Set<Player> playersMuted = new HashSet<>();

    protected abstract void handleChatEvent(AsyncPlayerChatEvent event);

    public BaseMuteManager(PunishmentFactory<T> factory, BaseStorage<T> storage, Class<T> type) {
        super(factory, storage, type);
        Bukkit.getPluginManager().registerEvents(this, TwistedPunishments.getInstance());
    }

    @Override
    protected boolean canConnect(T punishment, Player player, InetAddress address) {
        return true;
    }

    @Override
    protected void onPunish(T punishment, OfflinePlayer punished) {
        if (punished instanceof Player) playersMuted.add((Player) punished);
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (playersMuted.contains(player)) playersMuted.remove(player);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!playersMuted.contains(event.getPlayer())) return;
        handleChatEvent(event);
    }
}
