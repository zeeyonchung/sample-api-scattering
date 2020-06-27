package com.kakaopay.scattering.domain;

import com.kakaopay.scattering.domain.exception.AlreadyReceivedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("이미 받은 유저가 또 받으려고 하면 AlreadyReceivedException")
    @Test
    void assignTo_again() {
        Long userId = 100L;
        ScatteredMonies monies = ScatteredMonies.of(Arrays.asList(
                ScatteredMoney.of(1),
                ScatteredMoney.of(2),
                ScatteredMoney.of(3)));

        monies.assignOneTo(userId);

        assertThatThrownBy(() -> monies.assignOneTo(userId))
                .isInstanceOf(AlreadyReceivedException.class);
    }
}
