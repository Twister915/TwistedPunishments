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
