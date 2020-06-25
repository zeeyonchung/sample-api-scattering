package com.kakaopay.scattering.domain.exception;

public class MoneySumNotMatchedException extends MoneyDivideException {

    public MoneySumNotMatchedException(String message) {
        super(message);
    }
}
