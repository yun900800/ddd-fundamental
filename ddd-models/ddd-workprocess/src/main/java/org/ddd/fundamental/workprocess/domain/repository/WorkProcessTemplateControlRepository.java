package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplateControl;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WorkProcessTemplateControlRepository extends BaseHibernateRepository<WorkProcessTemplateControl>,
        JpaRepository<WorkProcessTemplateControl, WorkProcessTemplateId> {
    @Modifying
    @Query(" delete from WorkProcessTemplateControl")
    void deleteAllTemplateControls();
}
