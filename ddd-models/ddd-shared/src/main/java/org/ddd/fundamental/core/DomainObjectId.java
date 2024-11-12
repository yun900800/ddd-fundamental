package org.ddd.fundamental.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class DomainObjectId implements ValueObject{
    private final String uuid;

    @SuppressWarnings("unused")
    private DomainObjectId(){
        this.uuid = randomId(DomainObjectId.class).toUUID();
    }

    @JsonCreator
    public DomainObjectId(@NonNull String uuid){
        this.uuid = uuid;
    }

    /**
     * 根据不同的类型生成对应的uuid
     * @param idClass
     * @return
     * @param <ID>
     */
    public static <ID extends Serializable> ID randomId(@NonNull Class<ID> idClass){
        Objects.requireNonNull(idClass, "idClass must not be null");
        try {
            return idClass.getConstructor(String.class).newInstance(UUID.randomUUID().toString());
        } catch (Exception ex) {
            throw new RuntimeException("Could not create new instance of " + idClass, ex);
        }
    }

    /**
     * Returns the ID as a UUID string.
     */
    @JsonValue
    @NonNull
    public String toUUID() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainObjectId that = (DomainObjectId) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), uuid);
    }
}
