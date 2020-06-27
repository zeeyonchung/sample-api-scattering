package com.kakaopay.scattering.domain.exception;

public class AlreadyReceivedException extends MoneyAssignException {

    public AlreadyReceivedException(String message) {
        super(message);
    }
}
