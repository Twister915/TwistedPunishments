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
    @NonNull protected Integer lengthInSeconds = 0;
}
