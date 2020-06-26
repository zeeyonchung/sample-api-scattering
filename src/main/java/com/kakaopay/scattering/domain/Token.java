package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Token {

    static final int LENGTH = 3;

    private String token;

    private Token(String token) {
        this.token = token;
    }

    public static Token of(String token) {
        validate(token);
        return new Token(token);
    }

    private static void validate(String token) {
        if (token.length() != LENGTH) {
            throw new IllegalArgumentException("토큰의 길이가 일치하지 않습니다 : " + token.length());
        }
    }
}
