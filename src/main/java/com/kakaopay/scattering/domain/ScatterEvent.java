package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "scatterEvent", cascade = CascadeType.ALL)
    private List<Receiver> receivers;

    @Builder
    public ScatterEvent(Token token,
                        Scatterer scatterer,
                        ScatteredMonies scatteredMonies,
                        List<Receiver> receivers) {
        this.token = token;
        this.scatterer = scatterer;
        this.scatteredMonies = scatteredMonies;
        this.receivers = receivers;
    }
}




