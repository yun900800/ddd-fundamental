package org.ddd.fundamental.income.domain;

import java.util.List;

public class IncomeBonusService {

    // 这个方法将计算圣诞节的收入和奖金的责任放置在服务类中;
    // 另一种方式就是将这个计算责任放置在Incomes中
    // 区别就是如果奖金规则一致的话放置在Incomes类中合适
    // 如果每个收入规则不一样的话放置在服务类中合适
    public void applyChristmasBonus(List<Income> incomes){
        for (Income income: incomes) {
            float bonus = new ChristmasBonusRule().compute(income);
            income.setAmount(bonus+income.getAmount());
        }
    }

    //注意比较这个方法和上面方法之间的区别?哪个是面向过程,哪个是面向对象
    public void applyBonus(Incomes incomes) {
        incomes.applyBonus(new ChristmasBonusRule());
    }
}
