package org.ddd.fundamental.visa.securityCheck.infrastructure;

import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityCheck;
import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityCheckId;
import org.ddd.fundamental.visa.securityCheck.domain.repository.SecurityCheckRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityCheckRepositoryImpl implements SecurityCheckRepository {
    @Override
    public Long generateId() {
        return null;
    }

    @Override
    public void save(SecurityCheck securityCheck) {

    }

    @Override
    public void sendForSource1SecurityCheck(SecurityCheck securityCheck) {

    }

    @Override
    public void sendForSource2SecurityCheck(SecurityCheck securityCheck) {

    }

    @Override
    public void sendForSource3SecurityCheck(SecurityCheck securityCheck) {

    }

    @Override
    public Optional<SecurityCheck> retrieveSecurityCheck(SecurityCheckId securityCheckId) {
        return Optional.empty();
    }
}
