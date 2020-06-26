package com.kakaopay.scattering.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ScatteredMoneyTest {

    @DisplayName("객체 생성 테스트")
    @Test
    void of() {
        BigDecimal money = BigDecimal.valueOf(1000);

        ScatteredMoney scatteredMoney = ScatteredMoney.of(money);

        assertThat(scatteredMoney).isEqualTo(ScatteredMoney.of(money));
    }

    @DisplayName("금액이 0원 초과가 아니면 IllegalArgumentException이 발생한다")
    @ParameterizedTest
    @CsvSource({"-1", "0"})
    void of_notOver0(BigDecimal money) {
        assertThatThrownBy(() -> ScatteredMoney.of(money))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0원");
    }

    @DisplayName("금액이 정수가 아니면 IllegalArgumentException이 발생한다")
    @ParameterizedTest
    @CsvSource({"0.1", "1000.1"})
    void of_notInteger(BigDecimal money) {
        assertThatThrownBy(() -> ScatteredMoney.of(money))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("정수");
    }

    @DisplayName("다른 나눠진 금액과의 합을 구한다")
    @ParameterizedTest
    @CsvSource({"1, 2, 3", "1000, 10000000000, 10000001000"})
    void sum(BigDecimal input1, BigDecimal input2, BigDecimal expected) {
        ScatteredMoney money1 = ScatteredMoney.of(input1);
        ScatteredMoney money2 = ScatteredMoney.of(input2);

        ScatteredMoney sum = money1.sum(money2);

        assertThat(sum).isEqualTo(ScatteredMoney.of(expected));
    }
}
