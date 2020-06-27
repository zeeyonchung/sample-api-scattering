package com.kakaopay.scattering.domain.exception;

public class SelfReceiveException extends MoneyAssignException {

    public SelfReceiveException(String message) {
        super(message);
    }
}
