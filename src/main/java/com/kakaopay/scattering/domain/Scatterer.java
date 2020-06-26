package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Scatterer {

    private Long memberId;

    public Scatterer(Long memberId) {
        this.memberId = memberId;
    }

    public boolean isEqualMember(long memberId) {
        return this.memberId == memberId;
    }
}
