package org.ddd.fundamental.visa.securityCheck.infrastructure.jpa;

import org.ddd.fundamental.visa.securityCheck.infrastructure.jpa.entity.SecurityCheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSecurityCheckRepository extends JpaRepository<SecurityCheckEntity, Long> {
}
