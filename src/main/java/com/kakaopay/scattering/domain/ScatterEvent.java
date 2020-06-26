package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ScatterEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Token token;
    private String roomId;
    private Scatterer scatterer;
    private ScatteredMonies scatteredMonies;

    @Builder
    public ScatterEvent(Token token,
                        String roomId,
                        Scatterer scatterer,
                        ScatteredMonies scatteredMonies) {

        Objects.requireNonNull(token);
        Objects.requireNonNull(roomId);
        Objects.requireNonNull(scatterer);
        Objects.requireNonNull(scatteredMonies);

        this.token = token;
        this.roomId = roomId;
        this.scatterer = scatterer;
        this.scatteredMonies = scatteredMonies;

        scatteredMonies.setScatterEvent(this);
    }
}




