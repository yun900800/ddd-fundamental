package org.ddd.fundamental.day;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class Auditable implements ValueObject, Cloneable{

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long created;

    private Long updated;

    @SuppressWarnings("unused")
    private Auditable(){}

    public Auditable(Long created){
        this.createTime = LocalDateTime.now();
        this.updateTime = createTime;
        this.created = created;
        this.updated = created;
    }

    public Auditable changeUpdateTime(LocalDateTime time){
        this.updateTime = time;
        return this;
    }

    public Auditable changeUpdated(Long updated){
        this.updated = updated;
        return this;
    }

    public Auditable changeCreated(Long created){
        this.created = created;
        return this;
    }
    public Auditable changeCreateTime(LocalDateTime time){
        this.createTime = time;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public Long getCreated() {
        return created;
    }

    public Long getUpdated() {
        return updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auditable)) return false;
        Auditable auditable = (Auditable) o;
        return Objects.equals(createTime, auditable.createTime) && Objects.equals(updateTime, auditable.updateTime) && Objects.equals(created, auditable.created) && Objects.equals(updated, auditable.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createTime, updateTime, created, updated);
    }

    @Override
    public String toString() {
        return "Auditable{" +
                "createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }

    @Override
    public Auditable clone() {
        try {
            Auditable clone = (Auditable) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
