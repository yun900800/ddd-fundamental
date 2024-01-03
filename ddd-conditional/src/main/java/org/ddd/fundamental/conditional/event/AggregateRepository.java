package org.ddd.fundamental.conditional.event;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregateRepository extends CrudRepository<Aggregate, Long> {

}
