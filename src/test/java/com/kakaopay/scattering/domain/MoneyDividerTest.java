package com.kakaopay.scattering.domain;

import com.kakaopay.scattering.domain.exception.MoneyCountNotMatchedException;
import com.kakaopay.scattering.domain.exception.MoneySumNotMatchedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyDividerTest {

    @DisplayName("금액을 주어진 수량대로 나눈다")
    @Test
    void divide_count() {
        int count = 2;
        BigDecimal money = BigDecimal.valueOf(1000);
        ScatteredMonies monies = ScatteredMonies.of(Arrays.asList(ScatteredMoney.of(400), ScatteredMoney.of(600)));
        MoneyDivider moneyDivider = new MoneyDivider((m, c) -> monies);

        ScatteredMonies scatteredMonies = moneyDivider.divide(money, count);

        assertThat(scatteredMonies.size()).isEqualTo(count);
    }

    @DisplayName("인원수보다 작은 금액이면 IllegalArgumentException")
    @Test
    void divide_moneyLessThenCount() {
        int count = 30;
        BigDecimal money = BigDecimal.valueOf(10);
        MoneyDivider moneyDivider = new MoneyDivider((m, c) -> ScatteredMonies.of(Collections.emptyList()));

        assertThatThrownBy(() -> moneyDivider.divide(money, count))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("금액은 인원 수보다 커야");
    }

    @DisplayName("금액이 주어진 수량대로 나뉘지 않았으면 MoneyCountNotMatchedException")
    @Test
    void divide_countNotMatched() {
        int count = 2;
        BigDecimal money = BigDecimal.valueOf(1000);
        ScatteredMonies monies = ScatteredMonies.of(Collections.singletonList(ScatteredMoney.of(1000)));
        MoneyDivider moneyDivider = new MoneyDivider((m, c) -> monies);

        assertThatThrownBy(() -> moneyDivider.divide(money, count))
                .isInstanceOf(MoneyCountNotMatchedException.class)
                .hasMessageContaining("수량");
    }

    @DisplayName("나눠진 금액의 합계는 주어진 금액과 같다")
    @Test
    void divide_sum() {
        int count = 2;
        BigDecimal money = BigDecimal.valueOf(1000);
        ScatteredMonies monies = ScatteredMonies.of(Arrays.asList(ScatteredMoney.of(400), ScatteredMoney.of(600)));
        MoneyDivider moneyDivider = new MoneyDivider((m, c) -> monies);

        ScatteredMonies scatteredMonies = moneyDivider.divide(money, count);
        ScatteredMoney sum = scatteredMonies.sum();

        assertThat(sum.getMoney().compareTo(money)).isEqualTo(0);
    }

    @DisplayName("나눠진 금액의 합이 처음의 금액과 일치하지 않으면 MoneySumNotMatchedException")
    @Test
    void divide_sumNotMatched() {
        int count = 2;
        BigDecimal money = BigDecimal.valueOf(1000);
        ScatteredMonies monies = ScatteredMonies.of(Arrays.asList(ScatteredMoney.of(100), ScatteredMoney.of(200)));
        MoneyDivider moneyDivider = new MoneyDivider((m, c) -> monies);

        assertThatThrownBy(() -> moneyDivider.divide(money, count))
                .isInstanceOf(MoneySumNotMatchedException.class)
                .hasMessageContaining("합계");
    }
}
