package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessId;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;

import java.util.List;
import java.util.Set;

public interface WorkProcessTemplateRepository extends BaseRepository<WorkProcessTemplate, WorkProcessId> {
    List<WorkProcessTemplate> findByIdIn(Set<WorkProcessId> ids);
}
