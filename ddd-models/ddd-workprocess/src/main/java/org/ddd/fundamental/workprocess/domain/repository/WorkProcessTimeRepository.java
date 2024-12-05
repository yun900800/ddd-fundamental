package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTimeEntity;
import org.ddd.fundamental.workprocess.value.WorkProcessTimeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkProcessTimeRepository extends BaseHibernateRepository<WorkProcessTimeEntity> ,
        JpaRepository<WorkProcessTimeEntity, WorkProcessTimeId> {
}
