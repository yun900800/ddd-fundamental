package org.ddd.fundamental.core.repository;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.core.DomainObjectId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import javax.validation.constraints.NotNull;

@NoRepositoryBean
public interface BaseRepository<Aggregate extends AbstractAggregateRoot<ID>, ID extends DomainObjectId>
    extends JpaRepository<Aggregate,ID>, JpaSpecificationExecutor<Aggregate> {

    default @NotNull Aggregate getById(@NotNull ID id) { // <5>
        return findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}
