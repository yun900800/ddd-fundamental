package org.ddd.fundamental.factory.domain.repository;

import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.factory.domain.model.WorkStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WorkStationRepository extends JpaRepository<WorkStation, WorkStationId> {

    @Modifying
    @Query("delete from WorkStation w")
    void deleteWorkStations();
}
