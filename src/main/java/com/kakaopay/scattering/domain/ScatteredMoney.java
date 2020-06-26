package com.kakaopay.scattering.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class ScatteredMoney {

    public static final ScatteredMoney ZERO = new ScatteredMoney(0);

    private final long money;

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

    public ScatteredMoney sum(ScatteredMoney anotherMoney) {
        return ScatteredMoney.of(money + anotherMoney.money);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ScatteredMoney that = (ScatteredMoney) o;
        return Objects.equals(money, that.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(money);
    }
}
