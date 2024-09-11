package org.ddd.fundamental.repository.core.repository.impl;

import org.ddd.fundamental.repository.core.EntityModel;
import org.ddd.fundamental.repository.core.exception.PersistenceException;
import org.ddd.fundamental.repository.core.exception.SubPersistenceException;
import org.ddd.fundamental.repository.core.repository.UnitOfWorkRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class UnitOfWorkBaseTest {

    private UnitOfWorkBase unitOfWorkBase = new SimpleUnitOfWork();

    private UnitOfWorkRepository unitOfWorkRepository = new UnitOfWorkRepository<EmptyEntityModel>() {
        @Override
        public void persistNewCreated(EmptyEntityModel entityModel) throws SubPersistenceException {

        }

        @Override
        public void persistDeleted(EmptyEntityModel entityModel) throws SubPersistenceException {

        }

        @Override
        public void persistChanged(EmptyEntityModel entityModel) throws SubPersistenceException {

        }
    };

    @Test
    public void testRegisterNewCreated() {
        EmptyEntityModel emptyEntityModel = new EmptyEntityModel(10000L);
        unitOfWorkBase.registerNewCreated(emptyEntityModel,unitOfWorkRepository);
        Map<EntityModel,UnitOfWorkRepository> createdEntities = unitOfWorkBase.getCreatedEntities();
        Assert.assertEquals(createdEntities.size(),1);
        for (Map.Entry<EntityModel, UnitOfWorkRepository> entry: createdEntities.entrySet()) {
            EntityModel entityModel = entry.getKey();
            UnitOfWorkRepository unitOfWorkRepository1 = entry.getValue();
            Assert.assertEquals(entityModel, emptyEntityModel);
            Assert.assertEquals(unitOfWorkRepository1.getClass().getInterfaces()[0], UnitOfWorkRepository.class);
        }
        unitOfWorkBase.registerUpdated(emptyEntityModel,unitOfWorkRepository);
        Assert.assertEquals(unitOfWorkBase.getChangedEntities().size(),0);
        unitOfWorkBase.registerDeleted(emptyEntityModel,unitOfWorkRepository);
        Assert.assertEquals(unitOfWorkBase.getDeletedEntities().size(),0);
    }

    @Test
    public void testRegisterUpdated() {
        EmptyEntityModel emptyEntityModel = new EmptyEntityModel(10001L);
        unitOfWorkBase.registerUpdated(emptyEntityModel, unitOfWorkRepository);
        Map<EntityModel,UnitOfWorkRepository> changedEntities = unitOfWorkBase.getChangedEntities();
        Assert.assertEquals(changedEntities.size(),1);
        for (Map.Entry<EntityModel, UnitOfWorkRepository> entry: changedEntities.entrySet()) {
            EntityModel entityModel = entry.getKey();
            UnitOfWorkRepository unitOfWorkRepository1 = entry.getValue();
            Assert.assertEquals(entityModel, emptyEntityModel);
            Assert.assertEquals(unitOfWorkRepository1.getClass().getInterfaces()[0], UnitOfWorkRepository.class);
        }

        unitOfWorkBase.registerNewCreated(emptyEntityModel,unitOfWorkRepository);
        Assert.assertEquals(unitOfWorkBase.getCreatedEntities().size(),0);
        unitOfWorkBase.registerDeleted(emptyEntityModel,unitOfWorkRepository);
        Assert.assertEquals(unitOfWorkBase.getDeletedEntities().size(),0);
    }

    @Test
    public void testRegisterDeleted() {
        EmptyEntityModel emptyEntityModel = new EmptyEntityModel(10002L);
        unitOfWorkBase.registerDeleted(emptyEntityModel, unitOfWorkRepository);
        Map<EntityModel,UnitOfWorkRepository> deletedEntities = unitOfWorkBase.getDeletedEntities();
        Assert.assertEquals(deletedEntities.size(),1);
        for (Map.Entry<EntityModel, UnitOfWorkRepository> entry: deletedEntities.entrySet()) {
            EntityModel entityModel = entry.getKey();
            UnitOfWorkRepository unitOfWorkRepository1 = entry.getValue();
            Assert.assertEquals(entityModel, emptyEntityModel);
            Assert.assertEquals(unitOfWorkRepository1.getClass().getInterfaces()[0], UnitOfWorkRepository.class);
        }

        unitOfWorkBase.registerNewCreated(emptyEntityModel,unitOfWorkRepository);
        Assert.assertEquals(unitOfWorkBase.getCreatedEntities().size(),0);
        unitOfWorkBase.registerUpdated(emptyEntityModel,unitOfWorkRepository);
        Assert.assertEquals(unitOfWorkBase.getChangedEntities().size(),0);
    }



    private static class EmptyEntityModel extends EntityModel<Long>{

        public EmptyEntityModel(Long id) {
            super(id);
        }
    }
}
