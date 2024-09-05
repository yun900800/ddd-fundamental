package org.ddd.fundamental.income.domain;

import java.util.ArrayList;
import java.util.List;

public class Incomes {

    private List<Income> incomeList = new ArrayList<>();

    public void applyBonus(BonusRule bonusRule){
        for (Income income: incomeList){
            income.applyBonus(bonusRule);
        }
    }
}
