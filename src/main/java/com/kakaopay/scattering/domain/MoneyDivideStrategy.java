package com.kakaopay.scattering.domain;

import java.math.BigDecimal;
import java.util.List;

@FunctionalInterface
public interface MoneyDivideStrategy {
    List<ScatteredMoney> divide(BigDecimal money, int count);
}
