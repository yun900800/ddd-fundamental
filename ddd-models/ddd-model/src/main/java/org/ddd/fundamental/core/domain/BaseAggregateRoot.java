package org.ddd.fundamental.core.domain;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@MappedSuperclass
public abstract class BaseAggregateRoot<Id extends Serializable> extends BaseEntity<Id> {
    private final @Transient List<Object> domainEvents = new ArrayList<>(); // <2>

    protected void registerEvent(@NotNull Object event) { // <3>
        domainEvents.add(Objects.requireNonNull(event));
    }

    @AfterDomainEventPublication // <4>
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }

    @DomainEvents // <5>
    protected Collection<Object> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}
