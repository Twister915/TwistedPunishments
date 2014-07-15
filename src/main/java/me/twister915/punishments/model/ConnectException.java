package me.twister915.punishments.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public final class ConnectException extends Exception {
    private final String disconnectMessage;
}
