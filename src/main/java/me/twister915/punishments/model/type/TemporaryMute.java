package me.twister915.punishments.model.type;

import me.twister915.punishments.model.TemporaryPunishment;
import org.bukkit.OfflinePlayer;

import java.util.Date;

public final class TemporaryMute extends BasePunishment implements TemporaryPunishment {
    public TemporaryMute(boolean active, String reason, Date datePunished, String punisherIdentifier, OfflinePlayer punished, Integer lengthInSeconds) {
        super(active, reason, datePunished, punisherIdentifier, punished);
        this.lengthInSeconds = lengthInSeconds;
    }
}
