package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.equipment.domain.model.EquipmentRPAccount;
import org.ddd.fundamental.equipment.value.EquipmentRPAccountId;
import org.ddd.fundamental.factory.EquipmentId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EquipmentRPAccountRepository extends BaseHibernateRepository<EquipmentRPAccount>,
        BaseRepository<EquipmentRPAccount, EquipmentRPAccountId> {

    @Modifying
    @Query("delete from EquipmentRPAccount")
    void deleteAllAccounts();

    @Modifying
    @Query("delete from EquipmentRPAccount era where era.equipment.id =:equipmentId")
    void deleteAccountRelationByEquipmentId(@Param("equipmentId")EquipmentId equipmentId);
}
