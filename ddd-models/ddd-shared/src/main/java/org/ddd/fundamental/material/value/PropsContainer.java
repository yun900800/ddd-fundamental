package org.ddd.fundamental.material.value;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.ValueObject;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.*;

@Slf4j
@MappedSuperclass
@Embeddable
public class PropsContainer implements ValueObject {
    /**
     * 必填的字段容器,并且需要查询
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<String,String> requiredMap = new HashMap<>();

    /**
     * 可选的字段容器,只是用于显示
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<String,String> optionalMap = new HashMap<>();

    @Transient
    private final Set<String> requiredSet;

    @SuppressWarnings("unused")
    PropsContainer(){
        this.requiredSet = new HashSet<>();
    }

    private PropsContainer(Set<String> requiredSet,Map<String,String> requiredMap,
                                   Map<String,String> optionalMap){
        this.requiredSet = requiredSet;
        this.requiredMap = requiredMap;
        this.optionalMap = optionalMap;
    }

    public static class Builder {

        private final Set<String> requiredSet;

        private Map<String,String> requiredMap = new HashMap<>();

        /**
         * 可选的字段容器,只是用于显示
         */
        private Map<String,String> optionalMap = new HashMap<>();

        public Builder(Set<String> requiredSet){
            this.requiredSet = requiredSet;
        }

        public Builder addProperty(String key,String value){
            if (this.requiredSet.contains(key)) {
                this.requiredMap.put(key,value);
            } else {
                this.optionalMap.put(key,value);
            }
            return this;
        }

        public Builder addMap(Map<String,String> data) {
            for (Map.Entry<String,String> entry: data.entrySet()) {
                this.addProperty(entry.getKey(),entry.getValue());
            }
            return this;
        }

        private boolean validateRequiredProps() {
            for (String key: requiredSet) {
                if (!requiredMap.containsKey(key)) {
                    log.error("props:{}不存在",key);
                    return false;
                }
            }
            return true;
        }
        public PropsContainer build() {
            if (!validateRequiredProps()){
                throw new RuntimeException("必选的属性不存在.");
            }
            return new PropsContainer(
                    requiredSet,requiredMap,
                    optionalMap);
        }
    }

    public PropsContainer addOptionalProps(String key,String value) {
        this.optionalMap.put(key,value);
        return this;
    }

    public PropsContainer removeOptionalProps(String key){
        this.optionalMap.remove(key);
        return this;
    }


    public Map<String, String> getRequiredMap() {
        return new HashMap<>(requiredMap);
    }

    public Map<String, String> getOptionalMap() {
        return new HashMap<>(optionalMap);
    }

    public Set<String> getRequiredSet() {
        return new HashSet<>(requiredSet);
    }

    @Override
    public String toString() {
        return "MaterialPropsContainer{" +
                "requiredMap=" + requiredMap +
                ", optionalMap=" + optionalMap +
                ", requiredSet=" + requiredSet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropsContainer)) return false;
        PropsContainer container = (PropsContainer) o;
        return requiredSet.equals(container.requiredSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requiredSet);
    }
}
