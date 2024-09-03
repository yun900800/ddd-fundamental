package org.ddd.fundamental.visa.securityCheck.domain.repository;

import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityCheck;
import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityCheckId;

import java.util.Optional;

public interface SecurityCheckRepository {
    Long generateId();

    void save(SecurityCheck securityCheck);

    void sendForSource1SecurityCheck(SecurityCheck securityCheck);

    void sendForSource2SecurityCheck(SecurityCheck securityCheck);

    void sendForSource3SecurityCheck(SecurityCheck securityCheck);

    Optional<SecurityCheck> retrieveSecurityCheck(SecurityCheckId securityCheckId);
}
