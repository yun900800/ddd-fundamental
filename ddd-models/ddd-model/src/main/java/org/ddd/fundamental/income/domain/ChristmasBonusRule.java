package org.ddd.fundamental.income.domain;

public class ChristmasBonusRule implements BonusRule{
    @Override
    public float compute(Income income) {
        return income.getAmount() * 0.2f;
    }
}
