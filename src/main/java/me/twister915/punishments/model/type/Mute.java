package me.twister915.punishments.model.type;

import org.bukkit.OfflinePlayer;

import java.util.Date;

public final class Mute extends BasePunishment {
    public Mute(boolean active, String reason, Date datePunished, String punisherIdentifier, OfflinePlayer punished) {
        super(active, reason, datePunished, punisherIdentifier, punished);
    }
}
