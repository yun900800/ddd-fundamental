package org.ddd.fundamental.factory.domain.repository;

import io.hypersistence.utils.spring.repository.HibernateRepository;
import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.domain.model.MachineShop;
import org.ddd.fundamental.material.value.MaterialId;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MachineShopRepository extends BaseHibernateRepository<MachineShop>,
        BaseRepository<MachineShop, MachineShopId> {

    List<MachineShop> findByIdIn(List<MachineShopId> ids);

    @Query("from MachineShop")
    List<MachineShop> findAll();
}
