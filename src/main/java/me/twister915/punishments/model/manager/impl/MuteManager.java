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
import me.twister915.punishments.model.manager.BaseStorage;
import me.twister915.punishments.model.type.Mute;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class MuteManager extends BaseMuteManager<Mute> {
    public MuteManager(PunishmentFactory<Mute> factory, BaseStorage<Mute> storage) {
        super(factory, storage, Mute.class);
    }

    @Override
    protected void handleChatEvent(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        //TODO message
    }
}
