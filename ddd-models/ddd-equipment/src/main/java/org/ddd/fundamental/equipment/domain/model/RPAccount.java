package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.equipment.value.RPAccountId;
import org.ddd.fundamental.equipment.value.RPAccountValue;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "rpa_account")
public class RPAccount extends AbstractAggregateRoot<RPAccountId> {

    private RPAccountValue accountValue;

    public RPAccount(RPAccountValue accountValue){
        super(RPAccountId.randomId(RPAccountId.class));
        this.accountValue = accountValue;
    }

    private RPAccount(){}

    @Override
    public long created() {
        return 0;
    }

    public RPAccountValue getAccountValue() {
        return accountValue.clone();
    }

    @Override
    public String toString() {
        return "RPAccount{" +
                "accountValue=" + accountValue +
                '}';
    }
}
