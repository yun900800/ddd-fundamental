package org.ddd.fundamental.visa.visaApplication.application.event.listener;

import org.ddd.fundamental.common.exception.DomainException;
import org.ddd.fundamental.visa.securityCheck.application.event.SecurityCheckCompletedEvent;
import org.ddd.fundamental.visa.visaApplication.application.VisaApplicationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class VisaApplicationEventListener {
    private final VisaApplicationService visaApplicationService;

    public VisaApplicationEventListener(VisaApplicationService visaApplicationService) {
        this.visaApplicationService = visaApplicationService;
    }

    @EventListener
    public void handle(SecurityCheckCompletedEvent checkResponseEvent) throws DomainException {
        visaApplicationService.processSecurityCheckResponse(checkResponseEvent);
    }

}
