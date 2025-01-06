package org.ddd.fundamental.factory.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.day.YearModelId;
import org.ddd.fundamental.factory.domain.model.YearModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface YearModelRepository extends BaseHibernateRepository<YearModel>,
        JpaRepository<YearModel, YearModelId> {

    @Modifying
    @Query("delete from YearModel")
    void deleteAllYearModel();
}
