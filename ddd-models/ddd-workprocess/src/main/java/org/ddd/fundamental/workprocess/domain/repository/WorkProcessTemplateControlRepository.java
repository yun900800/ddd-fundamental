package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplateControlEntity;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WorkProcessTemplateControlRepository extends BaseHibernateRepository<WorkProcessTemplateControlEntity>,
        JpaRepository<WorkProcessTemplateControlEntity, WorkProcessTemplateId> {
    @Modifying
    @Query(" delete from WorkProcessTemplateControlEntity")
    void deleteAllTemplateControls();
}
