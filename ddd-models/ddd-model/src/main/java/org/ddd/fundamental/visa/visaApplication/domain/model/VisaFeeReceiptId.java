package org.ddd.fundamental.visa.visaApplication.domain.model;

import org.ddd.fundamental.common.ID;

public class VisaFeeReceiptId implements ID {

    private Long id;

    public VisaFeeReceiptId(Long id) {
        this.id = id;
    }

    @Override
    public Long value() {
        return id;
    }
}
