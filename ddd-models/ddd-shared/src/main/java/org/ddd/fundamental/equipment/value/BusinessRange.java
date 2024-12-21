package org.ddd.fundamental.equipment.value;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.day.range.DateRangeValue;

import javax.persistence.*;
import java.util.Objects;

/**
 *  泛型参数加上业务数据的组合来构造值对象
 *  能够提高代码的复用效率
 * @param <B>
 */
@Embeddable
@MappedSuperclass
public class BusinessRange<B> implements ValueObject, Cloneable {

    private B business;

    private String clazz;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "business_start", nullable = false)),
            @AttributeOverride(name = "end", column = @Column(name = "business_end", nullable = false)),
            @AttributeOverride(name = "desc", column = @Column(name = "business_desc", nullable = false))
    })
    private DateRangeValue dateRangeValue;

    @SuppressWarnings("unused")
    protected BusinessRange(){}

    @JsonCreator
    private BusinessRange(B business,
                          DateRangeValue dateRangeValue){
        this.business = business;
        this.dateRangeValue = dateRangeValue;
        this.clazz = business.getClass().getSimpleName();
    }

    public B getBusiness() {
        return business;
    }

    public DateRangeValue getDateRangeValue() {
        return dateRangeValue;
    }

    public String getClazz() {
        return clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessRange)) return false;
        BusinessRange<?> that = (BusinessRange<?>) o;
        return Objects.equals(business, that.business) && Objects.equals(dateRangeValue, that.dateRangeValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(business, dateRangeValue);
    }

    @Override
    public String toString() {
        return "BusinessRange{" +
                "business=" + business +
                ", dateRangeValue=" + dateRangeValue +
                ", clazz=" + clazz +
                '}';
    }

    @Override
    public BusinessRange<B> clone() {
        try {
            BusinessRange clone = (BusinessRange) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
