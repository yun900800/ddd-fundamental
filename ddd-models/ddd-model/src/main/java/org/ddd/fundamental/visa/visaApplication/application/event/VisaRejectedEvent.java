package org.ddd.fundamental.visa.visaApplication.application.event;

import org.ddd.fundamental.visa.visaApplication.domain.model.ApplicantId;
import org.ddd.fundamental.visa.visaApplication.domain.model.VisaApplicationId;
import org.springframework.context.ApplicationEvent;

public class VisaRejectedEvent extends ApplicationEvent {

    private VisaApplicationId id;
    public VisaRejectedEvent(Object source, VisaApplicationId id) {
        super(source);
        this.id = id;
    }
}
