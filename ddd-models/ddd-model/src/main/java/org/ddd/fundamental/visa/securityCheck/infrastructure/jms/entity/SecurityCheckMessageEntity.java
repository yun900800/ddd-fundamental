package org.ddd.fundamental.visa.securityCheck.infrastructure.jms.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SecurityCheckMessageEntity {
    private String passportNumber;
    private String country;
    private String city;
    private String postCode;
    private String address;
    private String phoneNumber;
}
