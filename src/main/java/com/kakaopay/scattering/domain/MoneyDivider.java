package com.kakaopay.scattering.domain;

import com.kakaopay.scattering.domain.exception.MoneyCountNotMatchedException;
import com.kakaopay.scattering.domain.exception.MoneySumNotMatchedException;

import java.math.BigDecimal;
import java.util.List;

public class MoneyDivider {

    private final MoneyDivideStrategy divideStrategy;

    public MoneyDivider(MoneyDivideStrategy divideStrategy) {
        this.divideStrategy = divideStrategy;
    }

    public List<ScatteredMoney> divide(BigDecimal money, int count) {
        List<ScatteredMoney> scatteredMonies = divideStrategy.divide(money, count);
        validate(scatteredMonies, money, count);
        return scatteredMonies;
    }

    private void validate(List<ScatteredMoney> scatteredMonies, BigDecimal money, int count) {
        validateCount(scatteredMonies, count);
        validateSum(scatteredMonies, money);
    }

    private void validateCount(List<ScatteredMoney> scatteredMonies, int count) {
        if (scatteredMonies.size() != count) {
            throw new MoneyCountNotMatchedException("요청 수와 나눠진 금액의 수량이 일치하지 않습니다");
        }
    }

    private void validateSum(List<ScatteredMoney> scatteredMonies, BigDecimal money) {
        BigDecimal sum = scatteredMonies.stream()
                .map(ScatteredMoney::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (sum.compareTo(money) != 0) {
            throw new MoneySumNotMatchedException("요청 금액과 나눠진 금액의 합계가 일치하지 않습니다");
        }
    }
}
