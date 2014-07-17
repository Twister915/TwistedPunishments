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

import org.bukkit.OfflinePlayer;

import java.util.Date;

/**
 * Represents any punishment made by the plugin.
 */
public interface Punishment {
    /**
     * The reason specified by the punisher as to why this punishment was issued to this user. Specified in the command for this punishment.
     * @return A {@link java.lang.String} as specified by the punisher.
     */
    String getReason();

    /**
     * The date this punishment was issued.
     * @return A {@link java.util.Date} object representing the time at which this punishment was created.
     */
    Date getDatePunished();

    /**
     * Returns {@code true} if this punishment is still active, or {@code false} if this punishment has been revoked.
     * @return A {@code boolean} which denotes this punishment as active or inactive.
     */
    boolean isActive();

    /**
     * Used by the {@link me.twister915.punishments.model.PunishmentManager} to set the punishment to inactive. <b>YOU ARE DISCOURAGED FROM CALLING THIS METHOD DIRECTLY</b>
     * @param value The value to set this to
     */
    @Deprecated void setActive(boolean value);

    /**
     * Gets the identifier of the punisher. Usually a {@link java.util.UUID} represented by a {@link java.lang.String} or the word "CONSOLE" in the event the punishment was nor performed by a player.
     * @return A {@link java.lang.String} identifying the punisher.
     */
    String getPunisherIdentifier();

    /**
     * The player who received the punishment.
     * @return The {@link org.bukkit.OfflinePlayer} representing the player punished.
     */
    OfflinePlayer getPunished();

    /**
     * Get the amount of seconds this punishment is set to last for. In permanent punishments, this value is 0.
     * @return The number of seconds this punishment is set to last for in an {@link java.lang.Integer} object.
     */
    Integer getLengthInSeconds();

    /**
     * Gets the identifier specified by the storage. This is used to track the binding between a punishment object and a DB entry. This value will be {@code null} in the event that this has not been saved to a database.
     * @return A {@link java.lang.String} representing the database identifier, or {@code null} if this has yet to be saved.
     */
    String getDBId();

    /**
     * Sets the identifier in the database for tracking purposes. This should never be set to {@code null} and should always be set by a class acting as {@link me.twister915.punishments.model.manager.BaseStorage}. <b>YOU ARE DISCOURAGED FROM CALLING THIS METHOD.</b>
     * @param id The {@link java.lang.String} representing the identifier of this punishment in the database.
     */
    @Deprecated void setDBId(String id);
}
