package org.ddd.fundamental.app.repository.user;

import org.ddd.fundamental.app.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel,String> {
}
