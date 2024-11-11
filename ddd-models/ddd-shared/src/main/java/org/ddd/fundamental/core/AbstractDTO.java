package org.ddd.fundamental.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;

import javax.persistence.Id;
import java.util.Objects;

public abstract class AbstractDTO<ID extends DomainObjectId> implements IdentifiableDomainObject<ID>{
    @Id
    @JsonProperty("id")
    protected ID id;

    private static final ObjectMapper mapper  = new ObjectMapper();

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

    protected String toJson() {
        String result = "";
        try {
            result  = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e){
            result = "error";
        }
        return result;
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
