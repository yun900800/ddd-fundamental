package org.ddd.fundamental.factory.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.day.CalendarTypeId;
import org.ddd.fundamental.factory.domain.model.CalendarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalendarTypeRepository extends BaseHibernateRepository<CalendarType>,
        JpaRepository<CalendarType, CalendarTypeId> {

    @Modifying
    @Query("delete from CalendarType")
    void deleteAllCalendarType();

    @Query("from CalendarType")
    List<CalendarType> findAll();
}
