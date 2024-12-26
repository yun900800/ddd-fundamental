package org.ddd.fundamental.event.material;

import org.ddd.fundamental.core.DomainEvent;
import org.ddd.fundamental.event.core.BaseDomainEvent;
import org.ddd.fundamental.event.core.DomainEventType;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;

import java.time.Instant;

public class ProductEventCreated extends BaseDomainEvent<MaterialMaster> implements DomainEvent {

    /**
     * 物料类型
     */
    private MaterialType materialType;

    /**
     * 产品id
     */
    private MaterialId productId;

    private Instant occurredOn;

    protected ProductEventCreated(){
        super(DomainEventType.MATERIAL,null);
    }

    protected ProductEventCreated(DomainEventType type, MaterialMaster data) {
        super(type, data);
    }

    private ProductEventCreated(DomainEventType type, MaterialMaster data,
                                MaterialType materialType,MaterialId productId){
        this(type,data);
        this.materialType = materialType;
        this.occurredOn = Instant.now();
        this.productId = productId;
    }

    public MaterialId getProductId() {
        return productId;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }

    public static ProductEventCreated create(DomainEventType type, MaterialMaster data,
                                             MaterialType materialType, MaterialId productId){
        return new ProductEventCreated(type, data, materialType,productId);
    }

    @Override
    protected Class<MaterialMaster> clazzT() {
        return MaterialMaster.class;
    }

    @Override
    protected Class<?> clazz() {
        return ProductEventCreated.class;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}
