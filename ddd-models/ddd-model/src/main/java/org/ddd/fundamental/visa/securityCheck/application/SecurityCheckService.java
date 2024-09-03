package org.ddd.fundamental.visa.securityCheck.application;

import org.ddd.fundamental.common.exception.DomainException;
import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityCheck;
import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityCheckId;
import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityCheckSource;
import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityStatus;
import org.ddd.fundamental.visa.securityCheck.domain.repository.SecurityCheckRepository;
import org.ddd.fundamental.visa.securityCheck.infrastructure.SecurityCheckRepositoryImpl;
import org.ddd.fundamental.visa.securityCheck.infrastructure.jms.entity.SecurityCheckResult;
import org.ddd.fundamental.visa.visaApplication.application.VisaApplicationService;
import org.ddd.fundamental.visa.visaApplication.application.event.SecurityCheckRequiredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SecurityCheckService {
    private final SecurityCheckRepository securityCheckRepository;

    private final VisaApplicationService visaApplicationService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public SecurityCheckService(SecurityCheckRepositoryImpl securityCheckRepository, VisaApplicationService visaApplicationService, ApplicationEventPublisher applicationEventPublisher) {
        this.securityCheckRepository = securityCheckRepository;
        this.visaApplicationService = visaApplicationService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void processSecurityCheckRequest(SecurityCheckRequiredEvent securityCheckRequiredEvent) {

        SecurityCheck securityCheck = new SecurityCheck(new SecurityCheckId(securityCheckRepository.generateId()), securityCheckRequiredEvent.getVisaApplicationId());
        securityCheck.validateForSubmission();
        securityCheckRepository.save(securityCheck);
        securityCheckRepository.sendForSource1SecurityCheck(securityCheck);
        securityCheckRepository.sendForSource2SecurityCheck(securityCheck);
        securityCheckRepository.sendForSource3SecurityCheck(securityCheck);

    }

    public void processSecurityCheckResponse(SecurityCheckResult checkResult, SecurityCheckSource checkSource) throws DomainException {

        SecurityCheck securityCheck = securityCheckRepository.
                retrieveSecurityCheck(new SecurityCheckId(checkResult.getSecurityCheckId()))
                .orElseThrow(() -> new DomainException("SecurityCheck couldn't find"));

        switch (checkSource) {
            case SOURCE1 : securityCheck.applySource1SecurityCheck(checkResult.getSecurityStatus());
            case SOURCE2 : securityCheck.applySource2SecurityCheck(checkResult.getSecurityStatus());
            case SOURCE3 : securityCheck.applySource3SecurityCheck(checkResult.getSecurityStatus());
        }
        if (getSecurityCheckFinalised(securityCheck)) {
            securityCheck.finaliseSecurityCheck();
        }

        securityCheckRepository.save(securityCheck);

        securityCheck.getDomainEvents().stream().forEach(applicationEventPublisher::publishEvent);
        securityCheck.clearDomainEvents();


    }

    public Boolean getSecurityCheckFinalised(SecurityCheck securityCheck) {
        return securityCheck.source1SecurityCheckStatus() != SecurityStatus.PENDING
                && securityCheck.source2SecurityCheckStatus() != SecurityStatus.PENDING
                && securityCheck.source3SecurityCheckStatus() != SecurityStatus.PENDING;
    }
}
