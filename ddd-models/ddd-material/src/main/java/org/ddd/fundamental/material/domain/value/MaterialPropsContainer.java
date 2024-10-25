package org.ddd.fundamental.material.domain.value;

import java.util.*;

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

    /**
     * 添加一个容器到物料属性容器
     * @param data
     * @return
     */
    public MaterialPropsContainer addMap(Map<String,String> data) {
        for (Map.Entry<String,String> entry: data.entrySet()) {
            this.addProperty(entry.getKey(),entry.getValue());
        }
        validate();
        return this;
    }

    private void validate(){
        for (String requiredKey: requiredSet) {
            if (!requiredMap.containsKey(requiredKey)) {
                throw new RuntimeException("必填属性:"+requiredKey+"不存在");
            }
        }
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
