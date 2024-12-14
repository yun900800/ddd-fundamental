package org.ddd.fundamental.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.Thread.ThreadSharedUtils;
import org.ddd.fundamental.core.tenant.TenantInfo;
import org.ddd.fundamental.day.Auditable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 核心的属性可以使用实体的生命周期注解进行实现
 * @param <ID>
 */
@MappedSuperclass
@Slf4j
public abstract class AbstractEntity<ID extends DomainObjectId> implements
        IdentifiableDomainObject<ID>, CreateInfo , Persistable<ID> {

    @Id
    @JsonProperty("id")
    private ID id;

    @Embedded
    private Auditable auditable;

    @Embedded
    private TenantInfo tenantInfo;

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
        this.tenantInfo = ThreadSharedUtils.getTenantInfo();
    }

    /**
     * Constructor for creating new entities.
     *
     * @param id the ID to assign to the entity.
     */
    protected AbstractEntity(@NonNull ID id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.auditable = new Auditable(created());
        this.tenantInfo = ThreadSharedUtils.getTenantInfo();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

//    public AbstractEntity setNew(boolean isNew){
//        this.isNew = isNew;
//        return this;
//    }

    @PostLoad
    public void loadedEntityNotNew() {
        this.isNew = false;
    }

    @PostPersist
    public void savedEntityNotNew(){
        this.isNew = false;
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

    public void changeUpdated(){
        this.changeUpdateTime(LocalDateTime.now());
        this.changeUpdated(created());
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
