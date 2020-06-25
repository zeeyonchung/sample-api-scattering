package com.kakaopay.scattering.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ScatteredMoney {

    private final BigDecimal money;

    private ScatteredMoney(BigDecimal money) {
        this.money = money;
    }

    public static ScatteredMoney of(BigDecimal money) {
        return new ScatteredMoney(money);
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
