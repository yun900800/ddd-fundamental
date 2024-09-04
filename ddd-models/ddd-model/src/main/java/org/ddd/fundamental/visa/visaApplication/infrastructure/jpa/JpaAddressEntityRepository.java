package org.ddd.fundamental.visa.visaApplication.infrastructure.jpa;

import org.ddd.fundamental.visa.visaApplication.infrastructure.jpa.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAddressEntityRepository extends JpaRepository<AddressEntity, Long> {
}
