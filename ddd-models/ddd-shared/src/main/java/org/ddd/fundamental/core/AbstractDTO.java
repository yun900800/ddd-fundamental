package org.ddd.fundamental.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.Id;
import java.util.Objects;

public abstract class AbstractDTO<ID extends DomainObjectId> implements IdentifiableDomainObject<ID>{
    @Id
    @JsonProperty("id")
    protected ID id;

    protected AbstractDTO() {
    }

    /**
     * Copy constructor
     *
     * @param source the entity to copy from.
     */
    protected AbstractDTO(@NonNull AbstractDTO<ID> source) {
        Objects.requireNonNull(source, "source must not be null");
        this.id = source.id;
    }

    /**
     * Constructor for creating new entities.
     *
     * @param id the ID to assign to the entity.
     */
    protected AbstractDTO(@NonNull ID id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
    }

}
