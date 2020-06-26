package com.kakaopay.scattering.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ScatteredMoneyTest {

    @DisplayName("객체 생성 테스트")
    @Test
    void of() {
        ScatteredMoney scatteredMoney = ScatteredMoney.of(1000);
        assertThat(scatteredMoney.isEqualMoney(1000)).isTrue();
    }

    @DisplayName("금액이 0원 초과가 아니면 IllegalArgumentException이 발생한다")
    @ParameterizedTest
    @CsvSource({"-1", "0"})
    void of_notOver0(long money) {
        assertThatThrownBy(() -> ScatteredMoney.of(money))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0원");
    }

    @DisplayName("다른 나눠진 금액과의 합을 구한다")
    @ParameterizedTest
    @CsvSource({"1, 2, 3", "1000, 10000000000, 10000001000"})
    void sum(long input1, long input2, long expected) {
        ScatteredMoney money1 = ScatteredMoney.of(input1);
        ScatteredMoney money2 = ScatteredMoney.of(input2);

        ScatteredMoney sum = money1.sum(money2);

        assertThat(sum.isEqualMoney(expected)).isTrue();
    }

    @DisplayName("할당 후엔 재할당이 불가능하다")
    @Test
    void canAssign() {
        ScatteredMoney scatteredMoney = ScatteredMoney.of(1000);

        scatteredMoney.assign();

        assertThat(scatteredMoney.canAssign()).isFalse();
    }

    @DisplayName("할당 후에 재할당하려고 하면 IllegalStateException")
    @Test
    void assign_alreadyAssigned() {
        ScatteredMoney scatteredMoney = ScatteredMoney.of(1000);
        scatteredMoney.assign();

        assertThatThrownBy(scatteredMoney::assign)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 할당");
    }
}
