package com.kakaopay.scattering.domain.exception;

public class AlreadyAssignedException extends MoneyAssignException {

    public AlreadyAssignedException(String message) {
        super(message);
    }
}
