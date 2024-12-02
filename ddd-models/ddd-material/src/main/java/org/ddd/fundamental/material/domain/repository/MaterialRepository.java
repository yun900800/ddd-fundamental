package org.ddd.fundamental.material.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.value.MaterialId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialRepository extends BaseHibernateRepository<Material>,BaseRepository<Material, MaterialId> {

    @Query(value = "SELECT * FROM material WHERE material_json -> ?1 = ?2", nativeQuery = true)
    List<Material> findByAttribute(String key, String value);

    List<Material> findByIdIn(List<MaterialId> ids);

    /**
     * 根据物料类型查询数据
     * @param materialType
     * @return
     */
    @Query(value = "SELECT * FROM material WHERE material_type = ?1", nativeQuery = true)
    List<Material> getByMaterialType(String materialType);

    @Modifying
    @Query("delete from Material")
    void deleteAllMaterials();

}
