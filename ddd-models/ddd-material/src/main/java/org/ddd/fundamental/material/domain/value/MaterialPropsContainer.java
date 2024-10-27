package org.ddd.fundamental.material.domain.value;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class MaterialPropsContainer {

    /**
     * 必填的字段容器,并且需要查询
     */
    private Map<String,String> requiredMap = new HashMap<>();

    /**
     * 可选的字段容器,只是用于显示
     */
    private Map<String,String> optionalMap = new HashMap<>();

    private final Set<String> requiredSet;

    public MaterialPropsContainer(Set<String> requiredSet,Map<String,String> requiredMap,
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
        public MaterialPropsContainer build() {
            if (!validateRequiredProps()){
                throw new RuntimeException("必选的属性不存在.");
            }
            return new MaterialPropsContainer(
                    requiredSet,requiredMap,
                    optionalMap);
        }
    }

    public MaterialPropsContainer removeProperty(String key){
        if (this.requiredSet.contains(key)) {
            throw new RuntimeException("必选属性不能移出");
        }
        this.requiredMap.remove(key);
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
        if (!(o instanceof MaterialPropsContainer)) return false;
        MaterialPropsContainer container = (MaterialPropsContainer) o;
        return requiredSet.equals(container.requiredSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requiredSet);
    }
}
