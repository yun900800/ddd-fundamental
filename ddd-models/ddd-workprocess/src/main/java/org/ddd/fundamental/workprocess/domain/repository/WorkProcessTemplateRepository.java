package org.ddd.fundamental.workprocess.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;

import java.util.List;
import java.util.Set;

public interface WorkProcessTemplateRepository extends BaseRepository<WorkProcessTemplate, WorkProcessTemplateId> {
    List<WorkProcessTemplate> findByIdIn(Set<WorkProcessTemplateId> ids);
}
