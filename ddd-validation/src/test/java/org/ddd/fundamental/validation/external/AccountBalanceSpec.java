package org.ddd.fundamental.validation.external;

import org.ddd.fundamental.validation.exception.OrderValidationException;
import org.ddd.fundamental.validation.exception.external.ValidationContext;
import org.ddd.fundamental.validation.exception.external.ValidationSpecificationBase;

public class AccountBalanceSpec extends ValidationSpecificationBase {
    private Customer customer;

    public AccountBalanceSpec(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void validate(ValidationContext validationContext) throws OrderValidationException {
        if (this.customer.getBalance() <= 0) {
            throw new OrderValidationException("order is valid");
        }
    }

}

class Customer {
    private int balance;

    public Customer(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }
}

