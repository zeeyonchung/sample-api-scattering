package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ScatteredMoney extends BaseTimeEntity {

    static final ScatteredMoney ZERO = new ScatteredMoney(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long money;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scatter_event_id")
    private ScatterEvent scatterEvent;

    private boolean assigned;

    private ScatteredMoney(long money) {
        this.money = money;
    }

    public static ScatteredMoney of(long money) {
        validate(money);
        return new ScatteredMoney(money);
    }

    private static void validate(long money) {
        if (money <= 0) {
            throw new IllegalArgumentException("금액은 0원 초과이어야 합니다 : " + money);
        }
    }

    public boolean isEqualMoney(long money) {
        return this.money == money;
    }

    void setScatterEvent(ScatterEvent event) {
        this.scatterEvent = event;
    }

    public ScatteredMoney sum(ScatteredMoney anotherMoney) {
        return ScatteredMoney.of(money + anotherMoney.money);
    }

    public Long assign() {
        if (this.assigned) {
            throw new IllegalStateException("이미 할당된 금액입니다 : " + id);
        }

        this.assigned = true;
        return this.id;
    }

    public boolean canAssign() {
        return !this.assigned;
    }
}
