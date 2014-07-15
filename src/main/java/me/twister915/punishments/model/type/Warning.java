package me.twister915.punishments.model.type;

import org.bukkit.OfflinePlayer;

import java.util.Date;

public final class Warning extends BasePunishment {
    public Warning(String reason, Date datePunished, String punisherIdentifier, OfflinePlayer punished) {
        super(false, reason, datePunished, punisherIdentifier, punished);
    }
}
