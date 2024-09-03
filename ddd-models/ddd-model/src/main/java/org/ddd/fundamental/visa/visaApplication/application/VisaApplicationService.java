package org.ddd.fundamental.visa.visaApplication.application;

import org.ddd.fundamental.common.exception.DomainException;
import org.ddd.fundamental.visa.securityCheck.application.event.SecurityCheckCompletedEvent;
import org.ddd.fundamental.visa.visaApplication.domain.model.*;
import org.ddd.fundamental.visa.visaApplication.domain.repository.VisaApplicationRepository;
import org.ddd.fundamental.visa.visaApplication.infrastructure.VisaApplicationRepositoryImpl;
import org.ddd.fundamental.visa.visaApplication.infrastructure.rest.command.VisaApplicationRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class VisaApplicationService {
    private final VisaApplicationRepository repository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public VisaApplicationService(VisaApplicationRepositoryImpl visaApplicationRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.repository = visaApplicationRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional(transactionManager = "transactionManager")
    public void processVisaApplication(VisaApplicationRequest request) throws DomainException {

        // 从前台请求组装值对象(护照信息, visa费用信息, 申请人信息,地址信息)
        PassportInformation passportInformation = new PassportInformation(request.getPassportNumber(), request.getPassportExpirationDate(), request.getPassportIssuingCountryCode());
        VisaFeeReceipt visaPayment = repository.getVisaFeeReceipt(request.getPaymentId())
                .orElseThrow(() -> new DomainException("Visa Fee Receipt couldn't found. "));
        Applicant applicant = repository.getApplicant(request.getApplicantId())
                .orElseThrow(() -> new DomainException("Applicant couldn't found. "));
        Address address = repository.getAddress(request.getApplicantId())
                .orElseThrow(() -> new DomainException("Address couldn't found. "));

        if (!repository.isCountryEligibleForVisa(passportInformation.country().getCountryCode())) {
            throw new DomainException("Applicant's country is not eligible for the selected purpose of visit.");
        }

       /*
        some other checks
       * */

        VisaApplication visaApplication = new VisaApplication(new VisaApplicationId(repository.generateId()),
                applicant, passportInformation, LocalDate.now(), request.getVisaType(), request.getIntendedArriveDate(), address, visaPayment);

        visaApplication.validateForSubmission();
        repository.save(visaApplication);

        visaApplication.getDomainEvents().stream().forEach(applicationEventPublisher::publishEvent);
        visaApplication.clearDomainEvents();
    }

    public void processSecurityCheckResponse(SecurityCheckCompletedEvent checkResponseEvent) throws DomainException {
        VisaApplication visaApplication = retreiveVisaApplication(checkResponseEvent.getVisaApplicationId());
        visaApplication.applySecurityCheck(checkResponseEvent.getSecurityStatus());
    }

    public VisaApplication retreiveVisaApplication(VisaApplicationId visaApplicationId) throws DomainException {
        return repository.retrieveVisaApplicationById(visaApplicationId);
    }


}
