package com.kakaopay.scattering.web;

import lombok.Getter;

@Getter
public class ReceiveResponse {
    private long receivedMoney;

    public ReceiveResponse(long receivedMoney) {
        this.receivedMoney = receivedMoney;
    }
}
