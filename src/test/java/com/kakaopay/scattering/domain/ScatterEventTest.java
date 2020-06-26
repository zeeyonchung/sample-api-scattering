package com.kakaopay.scattering.domain;

import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

public class ScatterEventTest {

    private ScatterEvent scatterEvent;

    @BeforeEach
    void setValidEvent() {
        Token token = Token.of("123");
        String roomId = "room13";
        Scatterer scatterer = new Scatterer(1L);
        ScatteredMonies scatteredMonies = ScatteredMonies.of(Arrays.asList(
                ScatteredMoney.of(1000), ScatteredMoney.of(1000), ScatteredMoney.of(1001)));

        this.scatterEvent = ScatterEvent.builder()
                    .token(token)
                    .roomId(roomId)
                    .scatteredMonies(scatteredMonies)
                    .scatterer(scatterer)
                    .build();
    }
}
