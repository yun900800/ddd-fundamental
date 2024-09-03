package org.ddd.fundamental.visa.visaApplication.infrastructure;

import org.ddd.fundamental.common.exception.DomainException;
import org.ddd.fundamental.visa.visaApplication.domain.model.*;
import org.ddd.fundamental.visa.visaApplication.domain.repository.VisaApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VisaApplicationRepositoryImpl implements VisaApplicationRepository {

    @Override
    public Long generateId() {
        return null;
    }

    @Override
    public VisaApplication retrieveVisaApplicationById(VisaApplicationId visaApplicationId) throws DomainException {
        return null;
    }

    @Override
    public void save(VisaApplication visaApplication) {

    }

    @Override
    public Optional<VisaFeeReceipt> getVisaFeeReceipt(Long paymentId) {
        return Optional.empty();
    }

    @Override
    public Optional<Applicant> getApplicant(Long applicantId) {
        return Optional.empty();
    }

    @Override
    public Optional<PassportInformation> getPassportInfo(Long applicantId) {
        return Optional.empty();
    }

    @Override
    public boolean isCountryEligibleForVisa(String countryCode) {
        return false;
    }

    @Override
    public Optional<Address> getAddress(Long applicantId) {
        return Optional.empty();
    }
}
