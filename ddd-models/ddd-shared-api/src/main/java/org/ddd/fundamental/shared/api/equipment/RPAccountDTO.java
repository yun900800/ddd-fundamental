package org.ddd.fundamental.shared.api.equipment;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.equipment.value.RPAccountId;
import org.ddd.fundamental.equipment.value.RPAccountValue;

public class RPAccountDTO extends AbstractDTO<RPAccountId> {

    @SuppressWarnings("unused")
    protected RPAccountDTO(){}

    private RPAccountValue accountValue;

    private RPAccountDTO(RPAccountId id,RPAccountValue accountValue){
        super(id);
        this.accountValue = accountValue;
    }

    public static RPAccountDTO create(RPAccountId id,RPAccountValue accountValue){
        return new RPAccountDTO(id,accountValue);
    }

    public RPAccountValue getAccountValue() {
        return accountValue.clone();
    }

    @Override
    public RPAccountId id() {
        return super.id;
    }

    @Override
    public String toString() {
        return "RPAccountDTO{" +
                "accountValue=" + accountValue +
                ", id=" + id() +
                '}';
    }
}
