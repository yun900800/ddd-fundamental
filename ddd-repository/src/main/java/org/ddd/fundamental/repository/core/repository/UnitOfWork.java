package org.ddd.fundamental.repository.core.repository;

import org.ddd.fundamental.repository.core.EntityModel;
import org.ddd.fundamental.repository.core.repository.vo.CommitHandlingResult;


/**
 * 工作单元接口类
 * 定义了需要持久化的聚合实体与哪些Repository来持久化这些聚合
 */
public interface UnitOfWork {

    void registerNewCreated(EntityModel entity, UnitOfWorkRepository<? extends EntityModel> repository);

    void registerUpdated(EntityModel entity, UnitOfWorkRepository<? extends EntityModel> repository);

    void registerDeleted(EntityModel entity, UnitOfWorkRepository<? extends EntityModel> repository);

    CommitHandlingResult commit();
}
