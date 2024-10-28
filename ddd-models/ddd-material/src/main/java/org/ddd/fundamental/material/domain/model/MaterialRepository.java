package org.ddd.fundamental.material.domain.model;

import org.ddd.fundamental.material.value.MaterialId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, MaterialId> {
}
