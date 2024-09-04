package org.ddd.fundamental.visa.visaApplication.infrastructure.jpa;

import org.ddd.fundamental.visa.visaApplication.infrastructure.jpa.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPassportEntityRepository extends JpaRepository<PassportEntity, Long> {
}
