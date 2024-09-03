package org.ddd.fundamental.visa.visaApplication.application.event;

import org.ddd.fundamental.visa.visaApplication.domain.model.VisaApplicationId;
import org.springframework.context.ApplicationEvent;

public class VisaApprovedEvent extends ApplicationEvent {
    private VisaApplicationId id;
    public VisaApprovedEvent(Object source,VisaApplicationId id) {
        super(source);
        this.id = id;
    }
}
