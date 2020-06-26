package com.kakaopay.scattering.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatCode;

public class ScatterEventTest {

    @DisplayName("객체 생성 테스트")
    @Test
    void build() {
        Token token = Token.of("123");
        ScatteredMonies scatteredMonies = ScatteredMonies.of(Arrays.asList(
                ScatteredMoney.of(1000), ScatteredMoney.of(1000), ScatteredMoney.of(1001)));
        Scatterer scatterer = new Scatterer(1L);

        assertThatCode(() -> ScatterEvent.builder()
                    .token(token)
                    .scatteredMonies(scatteredMonies)
                    .scatterer(scatterer)
                    .build())
                .doesNotThrowAnyException();
    }
}
