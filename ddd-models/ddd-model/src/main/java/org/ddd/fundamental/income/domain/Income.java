package org.ddd.fundamental.income.domain;

/**
 * 收入类
 */
public class Income {

    /**
     * 收入金额
     */
    private float amount;

    public void applyBonus(BonusRule bonusRule){
        float bonus = bonusRule.compute(this);
        amount += bonus;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
