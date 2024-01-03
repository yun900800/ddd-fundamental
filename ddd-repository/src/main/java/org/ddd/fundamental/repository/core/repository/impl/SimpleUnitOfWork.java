package org.ddd.fundamental.repository.core.repository.impl;

import org.ddd.fundamental.repository.core.exception.PersistenceException;
import org.ddd.fundamental.repository.core.utils.ApplicationContextProvider;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionTemplate;

public class SimpleUnitOfWork extends UnitOfWorkBase{
    @Override
    protected void persist() throws PersistenceException {
        TransactionTemplate transactionTemplate = ApplicationContextProvider.getBean(TransactionTemplate.class);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        transactionTemplate.setPropagationBehavior(Propagation.REQUIRES_NEW.value());

        Exception exception = transactionTemplate.execute(transactionStatus -> {
            try {
                persistDeleted();
                persistChanged();
                persistNewCreated();
                return null;
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                return e;
            }
        });
        if (exception != null) {
            throw new PersistenceException(exception);
        }
    }
}
