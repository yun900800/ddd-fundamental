package org.ddd.fundamental.validation.external;

public class OrderValidationService extends ValidationServiceBase {
    /**
     * 构建验证器
     *
     * @param validator 验证器
     */
    @Override
    protected void buildValidator(Validator validator) {
        Customer customer = this.constructAccount(validator);
        //账号状态验证
        validator.addSpecification(new AccountBalanceSpec(customer));
    }

    private Customer constructAccount(Validator validator) {
         String accountId = ((OrderValidationContext)validator.getContext()).getAccountId();
        //通过调用远程服务查询账户信息
         Customer customer = new Customer(30);
         return customer;
    }
}