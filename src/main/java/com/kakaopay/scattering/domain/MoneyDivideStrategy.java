package com.kakaopay.scattering.domain;

@FunctionalInterface
public interface MoneyDivideStrategy {
    ScatteredMonies divide(long money, int count);
}
