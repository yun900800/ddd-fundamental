package org.ddd.fundamental.visa.securityCheck.application.event;

import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityStatus;
import org.ddd.fundamental.visa.visaApplication.domain.model.VisaApplicationId;
import org.springframework.context.ApplicationEvent;

public class SecurityCheckCompletedEvent extends ApplicationEvent {

    private VisaApplicationId visaApplicationId;
    private SecurityStatus securityStatus;
    public SecurityCheckCompletedEvent(Object source, VisaApplicationId visaApplicationId) {
        super(source);
    }

    public VisaApplicationId getVisaApplicationId() {
        return visaApplicationId;
    }

    public void setSecurityStatus(SecurityStatus securityStatus) {
        this.securityStatus = securityStatus;
    }

    public SecurityStatus getSecurityStatus() {
        return securityStatus;
    }
}
