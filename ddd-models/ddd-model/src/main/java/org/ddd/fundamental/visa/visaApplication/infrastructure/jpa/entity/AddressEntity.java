package org.ddd.fundamental.visa.visaApplication.infrastructure.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ADDRESS")
public class AddressEntity {
    @Id
    private Long id;
    private String country;
    private String city;
    private String postCode;
    private String address;
    private String phoneNumber;
}
