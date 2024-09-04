package org.ddd.fundamental.visa.visaApplication.infrastructure.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "APPLICANT")
public class ApplicantEntity {
    @Id
    private Long id;
    private String fullName;
    private Date birthDate;
    private String nationality;



}
