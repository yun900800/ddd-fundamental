package org.ddd.fundamental.material.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.material.domain.model.MaterialRecord;
import org.ddd.fundamental.material.value.MaterialRecordId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MaterialRecordRepository extends BaseRepository<MaterialRecord, MaterialRecordId> {
}
