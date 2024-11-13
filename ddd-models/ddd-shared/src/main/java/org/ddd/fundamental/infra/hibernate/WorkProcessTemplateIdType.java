package org.ddd.fundamental.infra.hibernate;

import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;

public class WorkProcessTemplateIdType extends DomainObjectIdCustomType<WorkProcessTemplateId>{
    private static final DomainObjectIdTypeDescriptor<WorkProcessTemplateId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
            WorkProcessTemplateId.class, WorkProcessTemplateId::new);

    public WorkProcessTemplateIdType() {
        super(TYPE_DESCRIPTOR);
    }
}
