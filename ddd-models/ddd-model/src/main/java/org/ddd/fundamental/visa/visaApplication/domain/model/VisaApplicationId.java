package org.ddd.fundamental.visa.visaApplication.domain.model;

import org.ddd.fundamental.common.ID;

public class VisaApplicationId implements ID {
    private Long id;

    public VisaApplicationId(Long id) {
        this.id = id;
    }

    @Override
    public Long value() {
        return id;
    }
}
