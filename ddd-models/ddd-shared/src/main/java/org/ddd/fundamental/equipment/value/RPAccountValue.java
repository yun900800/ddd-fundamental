package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class RPAccountValue implements ValueObject, Cloneable {

    /**
     * 账户号
     */
    private int accountNo;

    /**
     * 缩写
     */
    private String abbreviation;

    /**
     * 描述
     */
    private String description;


    @SuppressWarnings("unused")
    private RPAccountValue(){}

    private RPAccountValue(int accountNo, String abbreviation, String description){
        this.accountNo = accountNo;
        this.description = description;
        this.abbreviation = abbreviation;
    }

    public static RPAccountValue create(int accountNo, String abbreviation, String description){
        return new RPAccountValue(accountNo,abbreviation,description);
    }

    public int getAccountNo() {
        return accountNo;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RPAccountValue)) return false;
        RPAccountValue rpAccount = (RPAccountValue) o;
        return accountNo == rpAccount.accountNo && Objects.equals(abbreviation, rpAccount.abbreviation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNo, abbreviation);
    }

    @Override
    public RPAccountValue clone() {
        try {
            RPAccountValue clone = (RPAccountValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "RPAccountValue{" +
                "accountNo=" + accountNo +
                ", abbreviation='" + abbreviation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
