package org.ddd.fundamental.repository.core.transaction;

import org.ddd.fundamental.repository.core.exception.CommitmentException;
import org.ddd.fundamental.repository.core.repository.UnitOfWork;
import org.ddd.fundamental.repository.core.repository.impl.RepositoryBase;
import org.ddd.fundamental.repository.core.repository.impl.SimpleUnitOfWork;
import org.ddd.fundamental.repository.core.repository.vo.CommitHandlingResult;

public final class TransactionScope {

    private UnitOfWork unitOfWork;

    private RepositoryBase[] repositoryBases;

    private TransactionScope(UnitOfWork unitOfWork, RepositoryBase[] repositoryBases) {
        this.unitOfWork = unitOfWork;
        this.repositoryBases = repositoryBases;
        if (unitOfWork != null && repositoryBases != null) {
            for (RepositoryBase repositoryBase : repositoryBases) {
                repositoryBase.setUnitOfWork(unitOfWork);//(2)
            }
        }
    }

    public static TransactionScope create(RepositoryBase... repositoryBases) {
        UnitOfWork unitOfWork = new SimpleUnitOfWork();//(1)
        TransactionScope transactionScope = new TransactionScope(unitOfWork, repositoryBases);
        return transactionScope;
    }

    public CommitHandlingResult commit() throws CommitmentException {
        if (this.unitOfWork == null) {
            throw new CommitmentException("unitOfWork:{} is null");
        }
        CommitHandlingResult result = this.unitOfWork.commit();//(3)
        if (unitOfWork != null && repositoryBases != null) {
            for (RepositoryBase repositoryBase : repositoryBases) {
                repositoryBase.removeUnitOfWork();//(4)
            }
        }
        return result;
    }
}

