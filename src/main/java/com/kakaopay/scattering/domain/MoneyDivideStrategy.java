package com.kakaopay.scattering.domain;

import java.math.BigDecimal;

@FunctionalInterface
public interface MoneyDivideStrategy {
    ScatteredMonies divide(BigDecimal money, int count);
}
