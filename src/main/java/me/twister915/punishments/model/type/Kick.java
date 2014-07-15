package me.twister915.punishments.model.type;

import org.bukkit.OfflinePlayer;

import java.util.Date;

public final class Kick extends BasePunishment {
    public Kick(String reason, Date datePunished, String punisherIdentifier, OfflinePlayer punished) {
        super(false, reason, datePunished, punisherIdentifier, punished);
    }
}
