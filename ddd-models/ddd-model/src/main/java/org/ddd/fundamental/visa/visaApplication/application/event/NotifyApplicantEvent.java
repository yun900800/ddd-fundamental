package org.ddd.fundamental.visa.visaApplication.application.event;

import org.ddd.fundamental.visa.visaApplication.domain.info.VisaApplicationStatus;
import org.ddd.fundamental.visa.visaApplication.domain.model.Address;
import org.ddd.fundamental.visa.visaApplication.domain.model.Applicant;
import org.ddd.fundamental.visa.visaApplication.domain.model.PassportInformation;
import org.springframework.context.ApplicationEvent;

public class NotifyApplicantEvent extends ApplicationEvent {

    private Address applicantAddress;
    private Applicant applicantPersonalInfo;
    private PassportInformation applicantPassportInfo;
    private VisaApplicationStatus status;

    public NotifyApplicantEvent(Object source, Address applicantAddress, Applicant applicantPersonalInfo, PassportInformation applicantPassportInfo,VisaApplicationStatus status) {
        super(source);
        this.applicantAddress = applicantAddress;
        this.applicantPersonalInfo = applicantPersonalInfo;
        this.applicantPassportInfo = applicantPassportInfo;
        this.status = status;
    }
}
