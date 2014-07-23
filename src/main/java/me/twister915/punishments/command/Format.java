/******************************************************************************
 * Copyright 2014 Joey Sacchini                                               *
 *                                                                            *
 * Licensed under the Apache License, Version 2.0 (the "License");            *
 * you may not use this file except in compliance with the License.           *
 * You may obtain a copy of the License at                                    *
 *                                                                            *
 *     http://www.apache.org/licenses/LICENSE-2.0                             *
 *                                                                            *
 * Unless required by applicable law or agreed to in writing, software        *
 * distributed under the License is distributed on an "AS IS" BASIS,          *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   *
 * See the License for the specific language governing permissions and        *
 * limitations under the License.                                             *
 ******************************************************************************/

package me.twister915.punishments.command;

import org.bukkit.ChatColor;

import java.text.MessageFormat;

public enum Format {
    PUNISHED_PERM("&cYou have &7{0}'d&c &7{1}&c for &7{2}&c."),
    FAILURE("&8{0}"),
    UNPUNISHED("&3You have &eun{0}'d&3 &e{1}&3.");

    private static final String PREFIX = $("&e&lTP&9&l>> &r");

    private final String format;

    Format(String format) {
        this.format = format;
    }

    public String using(Object... args) {
        return PREFIX + $(MessageFormat.format(format, args));
    }

    private static String $(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
