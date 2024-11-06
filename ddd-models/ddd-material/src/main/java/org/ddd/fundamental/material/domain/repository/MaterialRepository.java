package org.ddd.fundamental.material.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.value.MaterialId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends BaseRepository<Material, MaterialId> {
}
