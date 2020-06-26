package com.kakaopay.scattering.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ScatteredMoniesTest {

    @DisplayName("목록의 금액들의 총합을 구한다")
    @Test
    void sum() {
        ScatteredMonies monies = ScatteredMonies.of(Arrays.asList(
                ScatteredMoney.of(1),
                ScatteredMoney.of(2),
                ScatteredMoney.of(3)));

        ScatteredMoney sum = monies.sum();

        assertThat(sum.isEqualMoney(6)).isTrue();
    }
}
