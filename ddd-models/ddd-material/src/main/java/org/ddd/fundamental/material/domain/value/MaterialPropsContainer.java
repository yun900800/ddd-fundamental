package org.ddd.fundamental.material.domain.value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public MaterialPropsContainer(Set<String> requiredSet){
        this.requiredSet = requiredSet;
    }

    public MaterialPropsContainer addProperty(String key,String value){
        if (this.requiredSet.contains(key)) {
            this.requiredMap.put(key,value);
        } else {
            this.optionalMap.put(key,value);
        }
        return this;
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
}
