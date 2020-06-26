package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class Scatterer {

    @Column(name = "scatterer_id")
    private Long userId;

    public Scatterer(Long userId) {
        this.userId = userId;
    }
}
