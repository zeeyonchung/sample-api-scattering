package com.kakaopay.scattering.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class ScatteredMoney {

    private final BigDecimal money;

    private ScatteredMoney(BigDecimal money) {
        this.money = money;
        validate();
    }

    public static ScatteredMoney of(BigDecimal money) {
        return new ScatteredMoney(money);
    }

    public static  ScatteredMoney of(double money) {
        return new ScatteredMoney(BigDecimal.valueOf(money));
    }

    private void validate() {
        validateMoreThanZero();
        validateInteger();
    }

    private void validateMoreThanZero() {
        if (money.compareTo(BigDecimal.ZERO) < 1) {
            throw new IllegalArgumentException("금액은 0원 초과이어야 합니다 : " + money);
        }
    }

    private void validateInteger() {
        if (money.stripTrailingZeros().scale() > 0) {
            throw new IllegalArgumentException("금액은 정수이어야 합니다 : " + money);
        }
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
