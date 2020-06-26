package com.kakaopay.scattering.domain;

import com.kakaopay.scattering.domain.exception.MoneyCountNotMatchedException;
import com.kakaopay.scattering.domain.exception.MoneySumNotMatchedException;

import java.math.BigDecimal;

public class MoneyDivider {

    private final MoneyDivideStrategy divideStrategy;

    public MoneyDivider(MoneyDivideStrategy divideStrategy) {
        this.divideStrategy = divideStrategy;
    }

    public ScatteredMonies divide(BigDecimal money, int count) {
        validateMinimumMoney(money, count);

        ScatteredMonies scatteredMonies = divideStrategy.divide(money, count);
        validateResult(scatteredMonies, money, count);

        return scatteredMonies;
    }

    private void validateMinimumMoney(BigDecimal money, int count) {
        if (money.compareTo(BigDecimal.valueOf(count)) < 0) {
            throw new IllegalArgumentException("금액은 인원 수보다 커야 합니다");
        }
    }

    private void validateResult(ScatteredMonies scatteredMonies, BigDecimal money, int count) {
        validateCount(scatteredMonies, count);
        validateSum(scatteredMonies, money);
    }

    private void validateCount(ScatteredMonies scatteredMonies, int count) {
        if (scatteredMonies.size() != count) {
            throw new MoneyCountNotMatchedException("요청 수와 나눠진 금액의 수량이 일치하지 않습니다");
        }
    }

    private void validateSum(ScatteredMonies scatteredMonies, BigDecimal money) {
        BigDecimal sum = scatteredMonies.sum().getMoney();

        if (sum.compareTo(money) != 0) {
            throw new MoneySumNotMatchedException("요청 금액과 나눠진 금액의 합계가 일치하지 않습니다");
        }
    }
}
