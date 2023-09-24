package org.ddd.fundamental.repository.core.repository;

import org.ddd.fundamental.repository.core.EntityModel;

import java.util.List;

public interface Repository<TID extends Comparable<TID>, TEntity extends EntityModel<TID>> {

    TEntity findBy(TID id) throws RuntimeException;
    /**
     * 删除领域实体
     * @param entity 待删除的领域实体
     */
    void remove(TEntity entity);

    /**
     * 删除多个领域实体
     * @param entities 待删除的领域实体列表
     */
    void remove(List<TEntity> entities);

    /**
     *将领域实体存储至资源仓库中
     * @param entity 待存储的领域实体
     */
    void add(TEntity entity);

    /**
     * 将领域实体存储至资源仓库中
     * @param entities 待存储的领域实体列表
     */
    void add(List<TEntity> entities);

    /**
     *更新领域实体
     * @param entity 待更新的领域实体
     */
    void update(TEntity entity);

    /**
     *更新领域实体
     * @param entities 待更新的领域实体列表
     */
    void update(List<TEntity> entities);
}
