package org.ddd.fundamental.app.repository.user;

import org.ddd.fundamental.app.model.UserOldModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOldRepository extends JpaRepository<UserOldModel,String> {
}
