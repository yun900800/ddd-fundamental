package org.ddd.fundamental.material.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, MaterialId> {
}
