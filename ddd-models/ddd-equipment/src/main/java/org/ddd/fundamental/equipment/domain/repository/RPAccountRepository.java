package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.equipment.domain.model.RPAccount;
import org.ddd.fundamental.equipment.value.RPAccountId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RPAccountRepository extends BaseHibernateRepository<RPAccount>,
        BaseRepository<RPAccount, RPAccountId> {

    @Modifying
    @Query("delete from RPAccount")
    void deleteAllRPAccounts();

    @Query("from RPAccount")
    List<RPAccount> findAll();

    List<RPAccount> findByIdIn(List<RPAccountId> ids);
}
