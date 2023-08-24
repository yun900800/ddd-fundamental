package org.ddd.fundamental.user.repository.redis;

import org.ddd.fundamental.user.repository.redis.model.CardShopping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardShoppingRepository extends CrudRepository<CardShopping,String> {
}
