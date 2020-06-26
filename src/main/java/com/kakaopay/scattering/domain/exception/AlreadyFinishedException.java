package com.kakaopay.scattering.domain.exception;

public class AlreadyFinishedException extends MoneyAssignException {

    public AlreadyFinishedException(String message) {
        super(message);
    }
}
