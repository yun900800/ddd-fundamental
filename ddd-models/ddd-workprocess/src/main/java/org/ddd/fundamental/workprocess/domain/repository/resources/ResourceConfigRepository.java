package org.ddd.fundamental.workprocess.domain.repository.resources;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.workprocess.domain.model.resources.ResourceConfigEntity;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceConfigRepository extends BaseHibernateRepository<ResourceConfigEntity>,
        JpaRepository<ResourceConfigEntity, WorkProcessTemplateId> {

}
