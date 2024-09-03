package org.ddd.fundamental.visa.visaApplication.infrastructure.rest.command;

import lombok.Data;
import org.ddd.fundamental.visa.visaApplication.domain.info.VisaType;

import java.time.LocalDate;

@Data
public class VisaApplicationRequest {
    private Long applicantId;
    private String passportNumber;
    private LocalDate passportExpirationDate;
    private String passportIssuingCountryCode;
    private VisaType visaType;
    private LocalDate intendedArriveDate;
    private Long paymentId;
}
