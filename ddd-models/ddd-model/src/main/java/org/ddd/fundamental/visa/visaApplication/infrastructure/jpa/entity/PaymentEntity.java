package org.ddd.fundamental.visa.visaApplication.infrastructure.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "VISA_FEE_RECEIPT")
public class PaymentEntity {
    @Id
    private Long id;
    private BigDecimal chargedAmount;
    private Boolean valid;


}