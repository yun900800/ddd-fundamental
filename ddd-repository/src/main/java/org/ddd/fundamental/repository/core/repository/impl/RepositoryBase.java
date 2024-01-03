package org.ddd.fundamental.repository.core.repository.impl;

import org.ddd.fundamental.repository.core.EntityModel;
import org.ddd.fundamental.repository.core.exception.PersistenceException;
import org.ddd.fundamental.repository.core.repository.Repository;
import org.ddd.fundamental.repository.core.repository.UnitOfWork;
import org.ddd.fundamental.repository.core.repository.UnitOfWorkRepository;
import org.springframework.util.CollectionUtils;

import java.util.List;

public abstract class RepositoryBase<TID extends Comparable<TID>, TEntity extends EntityModel<TID>>
        implements Repository<TID, TEntity>, UnitOfWorkRepository<TEntity> {

    //工作单元
    ThreadLocal<UnitOfWork> unitOfWork = new ThreadLocal<>();

    /**
     * 将领域实体存储至资源仓库中
     * @param entity 待存储的领域实体
     */
    @Override
    public void add(TEntity entity) {
        if (entity == null) {
            return;
        }
        this.unitOfWork.get().registerNewCreated(entity,this);
    }

    @Override
    public void add(List<TEntity> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        for (TEntity entity: entityList) {
            this.unitOfWork.get().registerNewCreated(entity, this);
        }
    }


    @Override
    public void remove(TEntity entity) {
        if (entity == null) {
            return;
        }
        this.unitOfWork.get().registerDeleted(entity, this);
    }

    @Override
    public void remove(List<TEntity> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        for (TEntity entity: entityList) {
            this.unitOfWork.get().registerDeleted(entity, this);
        }
    }

    @Override
    public void update(TEntity entity) {
        if (entity == null) {
            return;
        }
        this.unitOfWork.get().registerUpdated(entity, this);
    }

    @Override
    public void update(List<TEntity> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        for (TEntity entity: entityList) {
            this.unitOfWork.get().registerUpdated(entity, this);
        }
    }

    public void setUnitOfWork(UnitOfWork unitOfWork) {
        this.unitOfWork.set(unitOfWork);
    }

    public void removeUnitOfWork() {
        this.unitOfWork.remove();
    }


    /**
     * 持久化新建的领域模型
     * @param entity 待持久化的领域模型
     */
    @Override
    public abstract void persistNewCreated(TEntity entity) throws PersistenceException;

    @Override
    public abstract void persistDeleted(TEntity entity) throws PersistenceException;

    @Override
    public abstract void persistChanged(TEntity entity) throws PersistenceException;
}
