package org.ddd.fundamental.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ddd.fundamental.day.Auditable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.NonNull;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity<ID extends DomainObjectId> implements
        IdentifiableDomainObject<ID>, CreateInfo , Persistable<ID> {

    @Id
    @JsonProperty("id")
    private ID id;

    @Embedded
    private Auditable auditable;

    @Transient
    private boolean isNew = true;


    /**
     * Default constructor
     */
    protected AbstractEntity() {
    }

    /**
     * Copy constructor
     *
     * @param source the entity to copy from.
     */
    protected AbstractEntity(@NonNull AbstractEntity<ID> source) {
        Objects.requireNonNull(source, "source must not be null");
        this.id = source.id;
        this.auditable = new Auditable(created());
    }

    /**
     * Constructor for creating new entities.
     *
     * @param id the ID to assign to the entity.
     */
    protected AbstractEntity(@NonNull ID id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.auditable = new Auditable(created());
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public AbstractEntity setIsNew(boolean isNew){
        this.isNew = isNew;
        return this;
    }

    public AbstractEntity<ID> changeUpdateTime(LocalDateTime time){
        this.auditable.changeUpdateTime(time);
        return this;
    }

    public AbstractEntity<ID> changeAuditable(Auditable auditable){
        this.auditable = auditable;
        return this;
    }

    public AbstractEntity<ID> changeUpdated(Long updated){
        this.auditable.changeUpdated(updated);
        return this;
    }

    @Override
    @NonNull
    public ID id() {
        return id;
    }
    @Override
    public ID getId() {
        return id();
    }

    public void changeId(ID id){
        this.id = id;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass") // We do this with a Spring function
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }

        var other = (AbstractEntity<?>) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), id);
    }
}
