package org.ddd.fundamental.bom.domain.repository;

import org.ddd.fundamental.bom.ProductStructureDataId;
import org.ddd.fundamental.bom.domain.model.ProductStructureData;
import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.shared.api.bom.BomIdDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductStructureDataRepository extends BaseHibernateRepository<ProductStructureData> ,
        JpaRepository<ProductStructureData, ProductStructureDataId> {

    @Modifying
    @Query("delete from ProductStructureData")
    void deleteProductStructures();

    @Query("from ProductStructureData")
    List<ProductStructureData> findAll();

    @Query("SELECT distinct new org.ddd.fundamental.shared.api.bom.BomIdDTO(u.materialIdNode.productId) FROM ProductStructureData u")
    List<BomIdDTO> allBomIds();

    @Query("""
            select u from ProductStructureData u where u.materialIdNode.productId = :productId
    """)
    List<ProductStructureData> findByProductId(@Param("productId") MaterialId productId);

}
