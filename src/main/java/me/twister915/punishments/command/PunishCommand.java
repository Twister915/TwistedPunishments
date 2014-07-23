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

import com.google.common.base.Joiner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.twister915.punishments.TwistedPunishments;
import me.twister915.punishments.model.PunishException;
import me.twister915.punishments.model.Punishment;
import me.twister915.punishments.model.PunishmentManager;
import me.twister915.punishments.model.TemporaryPunishment;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public final class PunishCommand extends BaseTargetedCommand {
    private final PunishmentManager<?> punishmentManager;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        try {
            boolean isTemporary = TemporaryPunishment.class.isAssignableFrom(punishmentManager.getType());

            if (args.length < 2 || (isTemporary && args.length < 3))
                throw new PunishException("You have too few arguments!");
            OfflinePlayer targetedPlayer = getTargetedPlayer(args[0]);
            if (targetedPlayer == null) throw new PunishException("The player you specified does not exist!");

            String[] reasonParts;
            if (isTemporary) reasonParts = Arrays.copyOfRange(args, 3, args.length);
            else reasonParts = Arrays.copyOfRange(args, 2, args.length);
            String reason = Joiner.on(' ').join(reasonParts);

            String name = TwistedPunishments.getName(punishmentManager.getType()).toLowerCase();
            Punishment punish;
            if (!isTemporary) {
                punish = punishmentManager.punish(targetedPlayer, reason, commandSender);
            } else {
                Double length = TimeUtils.parseTime(args[2]);
                punish = punishmentManager.punish(targetedPlayer, reason, commandSender, length);
            }
            commandSender.sendMessage(Format.PUNISHED_PERM.using(name, punish.getPunished().getName(), punish.getReason()));
        } catch (PunishException e) {
            commandSender.sendMessage(Format.FAILURE.using(e.getMessage()));
        }
        return true;
    }

    private String formatLength(Double lengthInSeconds) {
        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) return super.onTabComplete(commandSender, command, s, strings);
        else return Collections.emptyList();
    }
}
