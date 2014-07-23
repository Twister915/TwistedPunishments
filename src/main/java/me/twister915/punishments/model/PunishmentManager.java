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

package me.twister915.punishments.model;

import com.google.common.collect.ImmutableSet;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.util.Set;

public interface PunishmentManager<T extends Punishment> {
    Class<T> getType();
    T punish(OfflinePlayer player, String reason, CommandSender punisher) throws PunishException;
    T punish(OfflinePlayer player, String reason, CommandSender punisher, Double lengthInSeconds) throws PunishException;
    void unPunish(OfflinePlayer player) throws PunishException;
    T getActivePunishmentFor(OfflinePlayer player) throws PunishException;
    ImmutableSet<T> getAllPunishmentsFor(OfflinePlayer player) throws PunishException;
    void purgePunishments(Set<T> punishments) throws PunishException;
    void onPlayerConnect(Player player, InetAddress address) throws ConnectException;
}
