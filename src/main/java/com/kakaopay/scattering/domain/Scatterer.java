package com.kakaopay.scattering.domain;

public class Scatterer {

    private final Long memberId;

    public Scatterer(Long memberId) {
        this.memberId = memberId;
    }

    public boolean isEqualMember(long memberId) {
        return this.memberId == memberId;
    }
}
