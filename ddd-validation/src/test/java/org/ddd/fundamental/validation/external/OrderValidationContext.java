package org.ddd.fundamental.validation.external;

public class OrderValidationContext extends ValidationContext{

    private String accountId;

    public OrderValidationContext(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }
}
