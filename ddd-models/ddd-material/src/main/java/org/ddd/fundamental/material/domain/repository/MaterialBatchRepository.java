package org.ddd.fundamental.material.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.material.domain.model.MaterialBatch;
import org.ddd.fundamental.material.domain.value.BatchId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MaterialBatchRepository extends BaseHibernateRepository<MaterialBatch>,
        BaseRepository<MaterialBatch, BatchId> {
    @Modifying
    @Query("delete from MaterialBatch")
    void deleteAllMaterialBatches();
}
