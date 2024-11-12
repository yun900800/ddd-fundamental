package org.ddd.fundamental.workprocess.value.resources;

import org.ddd.fundamental.core.ValueObject;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@MappedSuperclass
@Embeddable
public class ProductResources implements ValueObject {

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "product_resources")
    private Set<ProductResource> resources = new HashSet<>();

    @SuppressWarnings("unused")
    private ProductResources(){}

    public ProductResources(Set<ProductResource> resources){
        this.resources = resources;
    }

    public Set<ProductResource> getResources() {
        return new HashSet<>(resources);
    }

    public int resourceSize(){
        return resources.size();
    }

    public ProductResources addResource(ProductResource resource) {
        this.resources.add(resource);
        return this;
    }

    public ProductResources removeResource(ProductResource resource) {
        this.resources.remove(resource);
        return this;
    }

    @Override
    public String toString() {
        return "ProductResources{" +
                "resources=" + resources +
                '}';
    }
}
