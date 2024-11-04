package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessId;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessNew;

import java.util.List;
import java.util.Set;

public interface WorkProcessNewRepository extends BaseRepository<WorkProcessNew, WorkProcessId> {
    List<WorkProcessNew> findByIdIn(Set<WorkProcessId> ids);
}
