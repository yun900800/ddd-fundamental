package org.ddd.fundamental.equipment.domain.repository;

import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.equipment.domain.model.RPAccount;
import org.ddd.fundamental.equipment.value.RPAccountId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RPAccountRepository extends BaseRepository<RPAccount, RPAccountId> {

    @Modifying
    @Query("delete from RPAccount")
    void deleteAllRPAccounts();
}
