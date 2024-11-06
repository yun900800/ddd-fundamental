package org.ddd.fundamental.material.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.material.domain.model.MaterialRecord;
import org.ddd.fundamental.material.value.MaterialRecordId;

public interface MaterialRecordRepository extends BaseRepository<MaterialRecord, MaterialRecordId> {
}
