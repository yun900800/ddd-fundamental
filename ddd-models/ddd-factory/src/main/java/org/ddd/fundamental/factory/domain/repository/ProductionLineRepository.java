package org.ddd.fundamental.factory.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductionLineRepository extends BaseRepository<ProductionLine, ProductionLineId> {
    @Modifying
    @Query("delete from ProductionLine p")
    void deleteProductionLines();
}
