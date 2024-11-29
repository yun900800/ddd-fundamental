package org.ddd.fundamental.factory.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.domain.model.MachineShop;
import org.ddd.fundamental.material.value.MaterialId;

import java.util.List;

public interface MachineShopRepository extends BaseRepository<MachineShop, MachineShopId> {

    List<MachineShop> findByIdIn(List<MachineShopId> ids);
}
