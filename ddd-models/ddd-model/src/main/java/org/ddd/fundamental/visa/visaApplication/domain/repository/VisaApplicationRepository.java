package org.ddd.fundamental.visa.visaApplication.domain.repository;

import org.ddd.fundamental.common.exception.DomainException;
import org.ddd.fundamental.visa.visaApplication.domain.model.*;

import java.util.Optional;

public interface VisaApplicationRepository {
    Long generateId();
    VisaApplication retrieveVisaApplicationById(VisaApplicationId visaApplicationId) throws DomainException;

    void save(VisaApplication visaApplication);

    Optional<VisaFeeReceipt> getVisaFeeReceipt(Long paymentId);
    Optional<Applicant> getApplicant(Long applicantId);

    Optional<PassportInformation> getPassportInfo(Long applicantId);

    boolean isCountryEligibleForVisa(String countryCode);

    Optional<Address> getAddress(Long applicantId);
}
