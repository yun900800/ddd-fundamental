package org.ddd.fundamental.invoice.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.ddd.fundamental.core.domain.BaseAggregateRoot;

import javax.persistence.*;

@Entity
@Table(name = "invoices")
public class Invoice extends BaseAggregateRoot<InvoiceId> {
    @Id
    @JsonProperty("id")
    @Column(columnDefinition = "bigint")
    private InvoiceId id;


    @Override

    public InvoiceId getId() {
        return super.getId();
    }
}
