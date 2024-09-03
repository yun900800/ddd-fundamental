package org.ddd.fundamental.visa.securityCheck.infrastructure.jms.entity;

import lombok.Data;
import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityStatus;

@Data
public class SecurityCheckResult {

    private Long securityCheckId;
    private SecurityStatus securityStatus;
}
