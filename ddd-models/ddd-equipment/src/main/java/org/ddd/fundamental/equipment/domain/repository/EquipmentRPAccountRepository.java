package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.equipment.domain.model.EquipmentRPAccount;
import org.ddd.fundamental.equipment.domain.value.EquipmentAccountId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRPAccountRepository extends BaseHibernateRepository<EquipmentRPAccount>,
        JpaRepository<EquipmentRPAccount, EquipmentAccountId> {
}
