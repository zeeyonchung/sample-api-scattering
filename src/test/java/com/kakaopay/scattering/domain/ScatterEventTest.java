package com.kakaopay.scattering.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;

public class ScatterEventTest {

    @DisplayName("객체 생성 테스트")
    @Test
    void build() {
        Token token = Token.of("123");
        ScatteredMonies scatteredMonies = ScatteredMonies.of(Arrays.asList(
                ScatteredMoney.of(1000), ScatteredMoney.of(1000), ScatteredMoney.of(1001)));
        Scatterer scatterer = new Scatterer(1L);
        List<Receiver> receivers = Arrays.asList(new Receiver(2L), new Receiver(3L), new Receiver(4L));

        assertThatCode(() -> ScatterEvent.builder()
                    .token(token)
                    .scatteredMonies(scatteredMonies)
                    .scatterer(scatterer)
                    .receivers(receivers)
                    .build())
                .doesNotThrowAnyException();
    }
}
