package org.ddd.fundamental.repository.core.repository;

import org.ddd.fundamental.repository.core.EntityModel;
import org.ddd.fundamental.repository.core.repository.vo.CommitHandlingResult;

public interface UnitOfWork {


    void registerNewCreated(EntityModel entity, UnitOfWorkRepository<? extends EntityModel> repository);

    void registerUpdated(EntityModel entity, UnitOfWorkRepository<? extends EntityModel> repository);

    void registerDeleted(EntityModel entity, UnitOfWorkRepository<? extends EntityModel> repository);

    CommitHandlingResult commit();
}
