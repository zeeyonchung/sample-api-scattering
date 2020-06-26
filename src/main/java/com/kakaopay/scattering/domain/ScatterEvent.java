package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ScatterEvent {

    @Id
    @GeneratedValue
    private Long id;

    private Token token;
    private Scatterer scatterer;
    private ScatteredMonies scatteredMonies;

    @Builder
    public ScatterEvent(Token token,
                        Scatterer scatterer,
                        ScatteredMonies scatteredMonies) {
        this.token = token;
        this.scatterer = scatterer;
        this.scatteredMonies = scatteredMonies;
    }
}




