package org.ddd.fundamental.workprocess.value;

import org.ddd.fundamental.core.ValueObject;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Embeddable
public class ProductResources implements ValueObject {

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "product_resources")
    private List<ProductResource> resources = new ArrayList<>();

    @SuppressWarnings("unused")
    private ProductResources(){}

    public ProductResources(List<ProductResource> resources){
        this.resources = resources;
    }

    public List<ProductResource> getResources() {
        return new ArrayList<>(resources);
    }

    public int resourceSize(){
        return resources.size();
    }

    public ProductResources addResource(ProductResource resource) {
        this.resources.add(resource);
        return this;
    }

    public ProductResources removeResource(ProductResource resource) {
        this.resources.add(resource);
        return this;
    }

    @Override
    public String toString() {
        return "ProductResources{" +
                "resources=" + resources +
                '}';
    }
}
