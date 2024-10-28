package org.ddd.fundamental.material.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.domain.model.Material;
import org.springframework.stereotype.Repository;

@Repository("materialRepository-1")
public interface MaterialRepository extends BaseRepository<Material, MaterialId> {
}
