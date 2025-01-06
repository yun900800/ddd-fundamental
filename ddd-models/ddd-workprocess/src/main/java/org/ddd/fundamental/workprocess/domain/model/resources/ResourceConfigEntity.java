package org.ddd.fundamental.workprocess.domain.model.resources;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.step.ResourceConfig;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Entity
@Table(name = "w_template_resource_config")
public class ResourceConfigEntity extends AbstractEntity<WorkProcessTemplateId> {

    private ResourceConfig resourceConfig;

    @SuppressWarnings("unused")
    protected ResourceConfigEntity(){
    }

    private ResourceConfigEntity(ResourceConfig resourceConfig){
        super(WorkProcessTemplateId.randomId(WorkProcessTemplateId.class));
        this.resourceConfig = resourceConfig;
    }

    public static ResourceConfigEntity create(ResourceConfig resourceConfig){
        return new ResourceConfigEntity(resourceConfig);
    }

    public int size(){
        return resourceConfig.size();
    }

    public ResourceConfigEntity addResource(ProductResource resource) {
        this.resourceConfig.addResource(resource);
        return this;
    }

    public ResourceConfigEntity removeResource(ProductResource resource) {
        this.resourceConfig.removeResource(resource);
        return this;
    }

    public Map<ProductResourceType, Set<ProductResource>> groupByType(){
        return this.resourceConfig.getConfigResources().stream().collect(Collectors.groupingBy(
                ProductResource::getResourceType,toSet()
        ));
    }

    public ResourceConfig getResourceConfig() {
        return resourceConfig.clone();
    }


    @Override
    public long created() {
        return 0;
    }
}
