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

import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.Punishment;
import org.bukkit.OfflinePlayer;

import java.util.Set;

public interface BaseStorage<T extends Punishment> {
    void store(T punishment) throws PunishException;
    void purge(Set<T> punishments) throws PunishException;
    Set<T> getForPlayer(OfflinePlayer player) throws PunishException;
}
