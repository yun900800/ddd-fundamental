package org.ddd.fundamental.repository.core.repository.impl;

import org.ddd.fundamental.repository.core.EntityModel;
import org.ddd.fundamental.repository.core.exception.PersistenceException;
import org.ddd.fundamental.repository.core.exception.ValidationException;
import org.ddd.fundamental.repository.core.repository.UnitOfWork;
import org.ddd.fundamental.repository.core.repository.UnitOfWorkRepository;
import org.ddd.fundamental.repository.core.repository.vo.CommitHandlingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class UnitOfWorkBase implements UnitOfWork {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnitOfWorkBase.class);

    /**
     * key存储新增的实体(聚合), value 存储将这些实体保存起来的工作单元repository
     */
    private Map<EntityModel, UnitOfWorkRepository> createdEntities = new HashMap<>();

    private Map<EntityModel, UnitOfWorkRepository> changedEntities = new HashMap<>();

    private Map<EntityModel, UnitOfWorkRepository> deletedEntities = new HashMap<>();

    @Override
    public void registerNewCreated(EntityModel entity, UnitOfWorkRepository<? extends EntityModel> repository) {
        if (entity == null || repository == null) {
            return;
        }
        if(this.deletedEntities.containsKey(entity ) || this.changedEntities.containsKey(entity)){
            return;
        }
        if(!this.createdEntities.containsKey(entity)){
            this.createdEntities.put(entity, repository);
        }
    }

    @Override
    public void registerUpdated(EntityModel entity, UnitOfWorkRepository<? extends EntityModel> repository) {
        if (entity == null || repository == null) {
            return;
        }
        if(this.deletedEntities.containsKey(entity ) || this.createdEntities.containsKey(entity)){
            return;
        }
        if(!this.changedEntities.containsKey(entity)){
            this.changedEntities.put(entity, repository);
        }
    }

    @Override
    public void registerDeleted(EntityModel entity, UnitOfWorkRepository<? extends EntityModel> repository) {
        if (entity == null || repository == null) {
            return;
        }
        if(this.changedEntities.containsKey(entity ) || this.createdEntities.containsKey(entity)){
            return;
        }
        if(!this.deletedEntities.containsKey(entity)){
            this.deletedEntities.put(entity, repository);
        }
    }

    @Override
    public CommitHandlingResult commit() {
        CommitHandlingResult result = new CommitHandlingResult();
        try {
            //this.validate();
            this.persist();
        } catch(ValidationException e) {
            LOGGER.error(e.getMessage(), e);
            result = new CommitHandlingResult(false, e.getMessage());
        } catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = new CommitHandlingResult(false, "");
        } finally {
            this.clear();
        }
        return result;
    }

    private void clear(){
        this.createdEntities.clear();
        this.changedEntities.clear();
        this.deletedEntities.clear();
    }

    abstract void persist() throws PersistenceException;

    //持久化新建的对象
    protected void persistNewCreated() throws PersistenceException {
        Iterator<Map.Entry<EntityModel, UnitOfWorkRepository>> iterator  = this.createdEntities.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<EntityModel,UnitOfWorkRepository> entry = iterator.next();
            //实际上持久化新增的聚合根
            entry.getValue().persistNewCreated(entry.getKey());
        }
    }

    protected void persistDeleted() throws PersistenceException {
        Iterator<Map.Entry<EntityModel, UnitOfWorkRepository>> iterator  = this.deletedEntities.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<EntityModel,UnitOfWorkRepository> entry = iterator.next();
            //实际上持久化删除的聚合根
            entry.getValue().persistNewCreated(entry.getKey());
        }
    }

    protected void persistChanged() throws PersistenceException {
        Iterator<Map.Entry<EntityModel, UnitOfWorkRepository>> iterator  = this.changedEntities.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<EntityModel,UnitOfWorkRepository> entry = iterator.next();
            //实际上持久化修改的聚合根
            entry.getValue().persistNewCreated(entry.getKey());
        }
    }

    public Map<EntityModel, UnitOfWorkRepository> getCreatedEntities() {
        return createdEntities;
    }

    public Map<EntityModel, UnitOfWorkRepository> getChangedEntities() {
        return changedEntities;
    }

    public Map<EntityModel, UnitOfWorkRepository> getDeletedEntities() {
        return deletedEntities;
    }

}
