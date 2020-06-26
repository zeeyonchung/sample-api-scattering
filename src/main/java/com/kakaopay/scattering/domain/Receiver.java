package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Receiver {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scatter_event_id")
    private ScatterEvent scatterEvent;

    private Long memberId;

    public Receiver(Long memberId) {
        this.memberId = memberId;
    }
}
