package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Token {

    static final int LENGTH = 3;

    @Column(name = "token")
    private String value;

    private Token(String value) {
        this.value = value;
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
