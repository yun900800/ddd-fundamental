package org.ddd.fundamental.invoice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ddd.fundamental.core.domain.AbstractEntity;
import org.ddd.fundamental.core.domain.DomainObjectId;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invoice_items")
public class InvoiceItem extends AbstractEntity<InvoiceItemId> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Column(name = "description", nullable = false)
    private String description;


    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "qty", nullable = false)
    private int quantity;

    @Column(name = "subtotal_excl_vat", nullable = false)
    private int subtotalExcludingVat;

    @Column(name = "subtotal_vat", nullable = false)
    private int subtotalVat;

    @Column(name = "subtotal_incl_vat", nullable = false)
    private int subtotalIncludingVat;

    @SuppressWarnings("unused") // Used by JPA only
    private InvoiceItem() {
    }

    InvoiceItem(@NonNull Invoice invoice, @NonNull String description, int quantity) {
        super(DomainObjectId.randomId(InvoiceItemId.class));
        setInvoice(invoice);
        setDescription(description);
        setQuantity(quantity);
    }

    @NonNull
    public Invoice invoice() {
        return invoice;
    }

    private void setInvoice(@NonNull Invoice invoice) {
        this.invoice = Objects.requireNonNull(invoice, "invoice must not be null");
    }

    @NonNull
    @JsonProperty("description")
    public String description() {
        return description;
    }

    private void setDescription(@NonNull String description) {
        this.description = Objects.requireNonNull(description, "description must not be null");
    }



    @JsonProperty("qty")
    public int quantity() {
        return quantity;
    }

    private void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
