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

package me.twister915.punishments.model.type;

import lombok.Data;
import lombok.NonNull;
import me.twister915.punishments.model.Punishment;
import org.bukkit.OfflinePlayer;

import java.util.Date;

@Data
abstract class BasePunishment implements Punishment {
    @NonNull protected boolean active;
    protected final String reason;
    protected final Date datePunished;
    protected final String punisherIdentifier;
    protected final OfflinePlayer punished;
    @NonNull protected Double lengthInSeconds = 0D;
    protected String dBId;
}
