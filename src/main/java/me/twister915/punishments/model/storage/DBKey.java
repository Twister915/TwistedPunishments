package me.twister915.punishments.model.storage;

public enum DBKey {
    ID("id"),
    PUNISH_TARGET("punished"),
    DATE_PUNISHED("date_punished"),
    REASON("reason"),
    ACTIVE("active"),
    LENGTH("length"),
    PUNISHER_ID("punisher_id");
    private final String key;

    DBKey(String key) {
        this.key = key;
    }

    public String toString() {
        return key;
    }
}
