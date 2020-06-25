package com.kakaopay.scattering.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ScatteredMoneyTest {

    @DisplayName("객체 생성 테스트")
    @Test
    void of() {
        BigDecimal money = BigDecimal.valueOf(1000);

        ScatteredMoney scatteredMoney = ScatteredMoney.of(money);

        assertThat(scatteredMoney).isEqualTo(ScatteredMoney.of(money));
    }

}
