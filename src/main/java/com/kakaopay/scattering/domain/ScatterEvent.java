package com.kakaopay.scattering.domain;

import lombok.Builder;

import java.util.List;

public class ScatterEvent {

    private Long id;
    private Token token;
    private ScatteredMonies scatteredMonies;
    private Scatterer scatterer;
    private List<Receiver> receivers;

    @Builder
    public ScatterEvent(Token token,
                        ScatteredMonies scatteredMonies,
                        Scatterer scatterer,
                        List<Receiver> receivers) {
        this.token = token;
        this.scatteredMonies = scatteredMonies;
        this.scatterer = scatterer;
        this.receivers = receivers;
    }
}




