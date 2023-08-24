package org.ddd.fundamental.user.repository.redis;

import org.ddd.fundamental.user.repository.redis.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel,String> {
}
