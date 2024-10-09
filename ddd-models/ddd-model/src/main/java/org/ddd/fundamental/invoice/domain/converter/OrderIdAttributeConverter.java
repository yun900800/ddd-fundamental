package org.ddd.fundamental.invoice.domain.converter;

import org.ddd.fundamental.invoice.domain.OrderId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderIdAttributeConverter implements AttributeConverter<OrderId, String> {

    @Override
    public String convertToDatabaseColumn(OrderId attribute) {
        return attribute == null ? null : attribute.toUUID();
    }

    @Override
    public OrderId convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new OrderId(dbData);
    }
}
