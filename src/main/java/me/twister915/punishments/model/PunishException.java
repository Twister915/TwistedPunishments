package me.twister915.punishments.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class PunishException extends Exception {
    private final String message;
}
