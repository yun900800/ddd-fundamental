package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.factory.ProductionLineId;

public class ProductionLineIdType extends DomainObjectIdCustomType<ProductionLineId>{
    /**
     * Creates a new {@code DomainObjectIdCustomType}. In your subclass, you should create a default constructor and
     * invoke this constructor from there.
     *
     * @param domainObjectIdTypeDescriptor the {@link DomainObjectIdTypeDescriptor} for the ID type.
     */
    private static final DomainObjectIdTypeDescriptor<ProductionLineId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            ProductionLineId.class, ProductionLineId::new);

    public ProductionLineIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
