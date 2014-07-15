package me.twister915.punishments.model;

import com.google.common.collect.ImmutableSet;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.net.InetAddress;

public interface PunishmentManager<T extends Punishment> {
    void punish(OfflinePlayer player, String reason, CommandSender punisher) throws PunishException;
    void punish(OfflinePlayer player, String reason, CommandSender punisher, Integer lengthInSeconds) throws PunishException;
    void unPunish(OfflinePlayer player) throws PunishException;
    T getActivePunishmentFor(OfflinePlayer player) throws PunishException;
    ImmutableSet<T> getAllPunishmentsFor(OfflinePlayer player) throws PunishException;
    void onPlayerConnect(Player player, InetAddress address) throws ConnectException;
}
