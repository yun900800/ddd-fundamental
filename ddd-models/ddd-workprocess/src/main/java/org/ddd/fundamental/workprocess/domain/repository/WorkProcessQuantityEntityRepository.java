package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessQuantityEntity;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkProcessQuantityEntityRepository extends
        BaseHibernateRepository<WorkProcessQuantityEntity>,
        JpaRepository<WorkProcessQuantityEntity, WorkProcessQuantityId>{
}
