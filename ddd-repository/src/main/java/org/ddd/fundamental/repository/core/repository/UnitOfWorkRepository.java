package org.ddd.fundamental.repository.core.repository;

import org.ddd.fundamental.repository.core.EntityModel;
import org.ddd.fundamental.repository.core.exception.PersistenceException;

/**
 * 持久化一个聚合对应的三种操作(新增，更新,删除)
 * @param <TEntity>
 */
public interface UnitOfWorkRepository<TEntity extends EntityModel> {

    /**
     * 持久化新建的领域模型
     * @param entity 待持久化的领域模型
     */
    void persistNewCreated(TEntity entity) throws PersistenceException;

    /**
     * 删除领域模型
     * @param entity 待删除的领域模型
     */
    void persistDeleted(TEntity entity) throws PersistenceException;

    /**
     * 持久化已变化的领域模型
     * @param entity 待持久化的领域模型
     */
    void persistChanged(TEntity entity) throws PersistenceException;
}
