package org.ddd.fundamental.factory.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.domain.model.MachineShop;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductionLineRepository extends BaseHibernateRepository<ProductionLine>,
        JpaRepository<ProductionLine, ProductionLineId> {
    @Modifying
    @Query("delete from ProductionLine p")
    void deleteProductionLines();

    @Query("from ProductionLine p")
    List<ProductionLine> findAll();

    List<ProductionLine> findByIdIn(List<ProductionLineId> ids);
}
