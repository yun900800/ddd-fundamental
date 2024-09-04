package org.ddd.fundamental.visa.visaApplication.infrastructure.jpa;

import org.ddd.fundamental.visa.visaApplication.infrastructure.jpa.entity.VisaApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaVisaApplicationEntityRepository extends JpaRepository<VisaApplicationEntity, String> {

    Optional<VisaApplicationEntity> findById(Long refNumber);

}
