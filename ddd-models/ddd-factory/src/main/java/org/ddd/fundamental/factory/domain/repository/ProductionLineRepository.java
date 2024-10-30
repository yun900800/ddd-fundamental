package org.ddd.fundamental.factory.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.domain.model.ProductionLine;

public interface ProductionLineRepository extends BaseRepository<ProductionLine, ProductionLineId> {
}
