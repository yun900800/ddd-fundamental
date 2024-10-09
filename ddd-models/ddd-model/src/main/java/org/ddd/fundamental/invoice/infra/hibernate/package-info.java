@TypeDefs({
        @TypeDef(defaultForType = InvoiceId.class, typeClass = InvoiceIdType.class),
        @TypeDef(defaultForType = InvoiceItemId.class, typeClass = InvoiceItemIdType.class)
})
package org.ddd.fundamental.invoice.infra.hibernate;

import org.ddd.fundamental.invoice.domain.InvoiceId;
import org.ddd.fundamental.invoice.domain.InvoiceItemId;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;