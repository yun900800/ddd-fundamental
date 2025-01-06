package org.ddd.fundamental.workprocess.value.step;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Embeddable
@MappedSuperclass
@Slf4j
public class ResourceConfig implements ValueObject,Cloneable {

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "config_resources")
    private Set<ProductResource> configResources;

    @SuppressWarnings("unused")
    protected ResourceConfig(){
    }

    private ResourceConfig(Set<ProductResource> configResources){
        this.configResources = configResources;
    }

    public static ResourceConfig create(Set<ProductResource> configResources){
        return new ResourceConfig(configResources);
    }

    public int size(){
        return configResources.size();
    }

    public ResourceConfig addResource(ProductResource resource) {
        this.configResources.add(resource);
        return this;
    }

    public ResourceConfig removeResource(ProductResource resource) {
        this.configResources.remove(resource);
        return this;
    }

    public Map<ProductResourceType, Set<ProductResource>> groupByType(){
        return this.configResources.stream().collect(Collectors.groupingBy(
                ProductResource::getResourceType,toSet()
        ));
    }

    @Override
    public String toString() {
        return objToString();
    }

    public Set<ProductResource> getConfigResources() {
        return new HashSet<>(configResources);
    }

    /**
     * 类中的对象必须复制一遍
     * @return
     */
    @Override
    public ResourceConfig clone() {
        try {

            ResourceConfig clone = (ResourceConfig) super.clone();
            Set<ProductResource> configResources = new HashSet<>(getConfigResources());
            clone.configResources = configResources;
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
