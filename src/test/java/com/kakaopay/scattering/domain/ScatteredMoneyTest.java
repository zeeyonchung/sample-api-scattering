package com.kakaopay.scattering.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    @ValueSource(ints = {-1, 0})
    void of_notOver0(int input) {
        BigDecimal money = BigDecimal.valueOf(input);

        assertThatThrownBy(() -> ScatteredMoney.of(money))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0원");
    }

    @DisplayName("금액이 정수가 아니면 IllegalArgumentException이 발생한다")
    @ParameterizedTest
    @ValueSource(doubles = {0.1, 1000.1})
    void of_notInteger(double input) {
        BigDecimal money = BigDecimal.valueOf(input);

        assertThatThrownBy(() -> ScatteredMoney.of(money))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("정수");
    }

}
