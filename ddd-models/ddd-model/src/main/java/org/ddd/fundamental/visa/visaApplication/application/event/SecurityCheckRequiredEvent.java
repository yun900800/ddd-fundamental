package org.ddd.fundamental.visa.visaApplication.application.event;

import org.ddd.fundamental.visa.visaApplication.domain.model.VisaApplication;
import org.springframework.context.ApplicationEvent;

public class SecurityCheckRequiredEvent extends ApplicationEvent {

    private VisaApplication visaApplication ;


    public SecurityCheckRequiredEvent(Object source, VisaApplication visaApplication) {
        super(source);
        this.visaApplication = visaApplication;
    }

    public VisaApplication getVisaApplicationId() {
        return visaApplication;
    }

}
