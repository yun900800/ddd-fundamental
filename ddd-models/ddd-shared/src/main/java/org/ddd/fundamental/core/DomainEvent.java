package org.ddd.fundamental.core;

import org.springframework.lang.NonNull;

import java.time.Instant;

/**
 * Interface for domain events.
 */
public interface DomainEvent extends DomainObject {

    /**
     * Returns the time and day on which the event occurred.
     */
    @NonNull
    Instant occurredOn();
}
