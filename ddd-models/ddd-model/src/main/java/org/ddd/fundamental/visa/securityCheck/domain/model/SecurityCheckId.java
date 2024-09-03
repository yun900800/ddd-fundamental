package org.ddd.fundamental.visa.securityCheck.domain.model;

import org.ddd.fundamental.common.ID;

public class SecurityCheckId implements ID {
    private Long id;

    public SecurityCheckId(Long id) {
        this.id = id;
    }

    public Long value() {
        return id;
    }
}
