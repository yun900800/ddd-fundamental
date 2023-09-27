package org.ddd.fundamental.core.domain.factory;

import org.ddd.fundamental.core.domain.entity.EntityModel;
import org.ddd.fundamental.core.exception.OrderCreationException;
import org.ddd.fundamental.core.vo.VOBase;

public abstract class EntityFactoryBase<TEntity extends EntityModel,TParameter extends VOBase> {

    public abstract TEntity create(TParameter voInfo) throws OrderCreationException;

    public abstract TEntity load(TParameter voInfo) throws OrderCreationException;
}
