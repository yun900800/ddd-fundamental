package org.ddd.fundamental.visa.visaApplication.domain.model;

import java.util.Date;

public class Applicant {

    private ApplicantId applicantId;

    private String fullName;

    private Date birthDate;

    private String nationality;

    public Applicant(ApplicantId id, String fullName, Date birthDate, String nationality) {
        this.applicantId = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.nationality = nationality;
    }

    public ApplicantId applicantId() {
        return applicantId;
    }

    public String fullName() {
        return fullName;
    }

    public Date birthDate() {
        return birthDate;
    }

    public String nationality() {
        return nationality;
    }
}
