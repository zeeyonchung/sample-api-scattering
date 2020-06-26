package com.kakaopay.scattering.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class ScatteredMoney {

    public static final ScatteredMoney ZERO = new ScatteredMoney(BigDecimal.ZERO);

    private final BigDecimal money;

    private ScatteredMoney(BigDecimal money) {
        this.money = money;
    }

    public static ScatteredMoney of(BigDecimal money) {
        validate(money);
        return new ScatteredMoney(money);
    }

    public static  ScatteredMoney of(double money) {
        return ScatteredMoney.of(BigDecimal.valueOf(money));
    }

    private static void validate(BigDecimal money) {
        validateMoreThanZero(money);
        validateInteger(money);
    }

    private static void validateMoreThanZero(BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("금액은 0원 초과이어야 합니다 : " + money);
        }
    }

    private static void validateInteger(BigDecimal money) {
        if (money.stripTrailingZeros().scale() > 0) {
            throw new IllegalArgumentException("금액은 정수이어야 합니다 : " + money);
        }
    }

    public ScatteredMoney sum(ScatteredMoney anotherMoney) {
        return ScatteredMoney.of(money.add(anotherMoney.money));
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
