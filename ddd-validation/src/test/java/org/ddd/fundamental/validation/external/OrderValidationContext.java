package org.ddd.fundamental.validation.external;

import org.ddd.fundamental.validation.exception.external.ValidationContext;

public class OrderValidationContext extends ValidationContext {

    private String accountId;

    public OrderValidationContext(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }
}
