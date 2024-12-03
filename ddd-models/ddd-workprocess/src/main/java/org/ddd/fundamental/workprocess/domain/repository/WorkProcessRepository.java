package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.workprocess.domain.model.WorkProcess;
import org.ddd.fundamental.workprocess.value.WorkProcessId;

public interface WorkProcessRepository extends BaseRepository<WorkProcess, WorkProcessId> {
}
