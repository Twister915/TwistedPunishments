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

package me.twister915.punishments;

import me.twister915.punishments.model.ConnectException;
import me.twister915.punishments.model.PunishmentManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public final class PunishmentListener implements Listener {
    public PunishmentListener() {
        Bukkit.getPluginManager().registerEvents(this, TwistedPunishments.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        for (PunishmentManager<?> punishmentManager : TwistedPunishments.getPunishmentManagers()) {
            try {
                punishmentManager.onPlayerConnect(event.getPlayer(), event.getAddress());
            } catch (ConnectException e) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, e.getMessage());
                return;
            }
        }
    }
}
