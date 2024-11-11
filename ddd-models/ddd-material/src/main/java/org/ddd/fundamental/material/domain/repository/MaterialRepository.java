package org.ddd.fundamental.material.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.value.MaterialId;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialRepository extends BaseRepository<Material, MaterialId> {

    @Query(value = "SELECT * FROM material WHERE material_json -> ?1 = ?2", nativeQuery = true)
    List<Material> findByAttribute(String key, String value);

    List<Material> findByIdIn(List<MaterialId> ids);

}
