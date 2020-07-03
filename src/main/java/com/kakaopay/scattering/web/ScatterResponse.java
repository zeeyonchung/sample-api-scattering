package com.kakaopay.scattering.web;

import com.kakaopay.scattering.domain.Token;
import lombok.Getter;

@Getter
public class ScatterResponse {

    private String token;

    public ScatterResponse(Token token) {
        this.token = token.getValue();
    }
}
