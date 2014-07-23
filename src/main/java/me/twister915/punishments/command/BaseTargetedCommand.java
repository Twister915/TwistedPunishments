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

package me.twister915.punishments.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

abstract class BaseTargetedCommand implements CommandExecutor, TabCompleter {
    public OfflinePlayer getTargetedPlayer(String arg) {
        Player player = Bukkit.getPlayer(arg);
        if (player != null) return player;
        try {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(arg));
            if (offlinePlayer != null) return offlinePlayer;
        } catch (IllegalArgumentException ignored) {}
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (offlinePlayer.getName().equalsIgnoreCase(arg)) return offlinePlayer;
        }
        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        ArrayList<String> strings1 = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().toLowerCase().startsWith(strings[strings.length-1])) strings1.add(player.getName());
        }
        return strings1;
    }
}
