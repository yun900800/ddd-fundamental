package org.ddd.fundamental.visa.visaApplication.infrastructure.jpa;

import org.ddd.fundamental.visa.visaApplication.infrastructure.jpa.entity.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaApplicantEntityRepository extends JpaRepository<ApplicantEntity, Long> {
}
