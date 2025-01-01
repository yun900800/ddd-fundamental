package org.ddd.fundamental.infra.query;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends BaseHibernateRepository<User>,
        JpaSpecificationExecutor<User> {
}
