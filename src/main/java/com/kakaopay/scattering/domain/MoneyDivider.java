package com.kakaopay.scattering.domain;

import com.kakaopay.scattering.domain.exception.MoneyCountNotMatchedException;
import com.kakaopay.scattering.domain.exception.MoneySumNotMatchedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MoneyDivider {

    private final MoneyDivideStrategy divideStrategy;

    public ScatteredMonies divide(long money, int count) {
        validateMinimumMoney(money, count);

        ScatteredMonies scatteredMonies = divideStrategy.divide(money, count);
        validateResult(scatteredMonies, money, count);

        return scatteredMonies;
    }

    private void validateMinimumMoney(long money, int count) {
        if (money < count) {
            throw new IllegalArgumentException("금액은 인원 수보다 커야 합니다");
        }
    }

    private void validateResult(ScatteredMonies scatteredMonies, long money, int count) {
        validateCount(scatteredMonies, count);
        validateSum(scatteredMonies, money);
    }

    private void validateCount(ScatteredMonies scatteredMonies, int count) {
        if (scatteredMonies.size() != count) {
            throw new MoneyCountNotMatchedException("요청 수와 나눠진 금액의 수량이 일치하지 않습니다");
        }
    }

    private void validateSum(ScatteredMonies scatteredMonies, long money) {
        ScatteredMoney sum = scatteredMonies.sum();

        if (!sum.isEqualMoney(money)) {
            throw new MoneySumNotMatchedException("요청 금액과 나눠진 금액의 합계가 일치하지 않습니다");
        }
    }
}
