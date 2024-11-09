package org.ddd.fundamental.material.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.material.domain.model.MaterialBatch;
import org.ddd.fundamental.material.domain.value.BatchId;

public interface MaterialBatchRepository extends BaseRepository<MaterialBatch, BatchId> {
}
