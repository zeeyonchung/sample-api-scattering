package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "receive_history_id")
    private ReceiveHistory receiveHistory;

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

    public ScatteredMoney assignTo(Long userId) {
        if (!canAssign()) {
            throw new IllegalStateException("할당 불가능한 금액입니다");
        }

        saveReceiveHistory(userId);
        return this;
    }

    private void saveReceiveHistory(Long userId) {
        this.receiveHistory = new ReceiveHistory(new Receiver(userId));
    }

    public boolean canAssign() {
        return this.receiveHistory == null;
    }

    public boolean isAssignedTo(Long userId) {
        if (this.receiveHistory == null) {
            return false;
        }

        return this.receiveHistory.isEqualReceiver(new Receiver(userId));
    }
}
