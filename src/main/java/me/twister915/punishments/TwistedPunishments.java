package me.twister915.punishments;

import lombok.Getter;
import me.twister915.punishments.model.Punishment;
import org.bukkit.plugin.java.JavaPlugin;

public final class TwistedPunishments extends JavaPlugin {
    @Getter private static TwistedPunishments instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    public static String getName(Class<? extends Punishment> punishmentClass) {
        return punishmentClass.getSimpleName();
    }
}
