package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.bom.ProductStructureDataId;

public class ProductStructureDataIdType extends DomainObjectIdCustomType<ProductStructureDataId>{

    /**
     * Creates a new {@code DomainObjectIdCustomType}. In your subclass, you should create a default constructor and
     * invoke this constructor from there.
     *
     * @param domainObjectIdTypeDescriptor the {@link DomainObjectIdTypeDescriptor} for the ID type.
     */
    private static final DomainObjectIdTypeDescriptor<ProductStructureDataId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            ProductStructureDataId.class, ProductStructureDataId::new);

    public ProductStructureDataIdType() {
        super(TYPE_DESCRIPTOR);
    }

}
