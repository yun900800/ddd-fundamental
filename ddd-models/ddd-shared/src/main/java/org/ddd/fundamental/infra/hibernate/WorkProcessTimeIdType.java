package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.workprocess.value.WorkProcessTimeId;

public class WorkProcessTimeIdType extends DomainObjectIdCustomType<WorkProcessTimeId>{
    /**
     * Creates a new {@code DomainObjectIdCustomType}. In your subclass, you should create a default constructor and
     * invoke this constructor from there.
     *
     * @param domainObjectIdTypeDescriptor the {@link DomainObjectIdTypeDescriptor} for the ID type.
     */
    private static final DomainObjectIdTypeDescriptor<WorkProcessTimeId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            WorkProcessTimeId.class, WorkProcessTimeId::new);

    public WorkProcessTimeIdType() {
            super(TYPE_DESCRIPTOR);
    }
}
