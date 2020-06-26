package com.kakaopay.scattering.domain;

import java.util.Objects;

public class Token {

    private static final int LENGTH = 3;

    private final String token;

    private Token(String token) {
        this.token = token;
    }

    public static Token of(String token) {
        validate(token);
        return new Token(token);
    }

    private static void validate(String token) {
        if (token.length() != LENGTH) {
            throw new IllegalArgumentException("토큰의 길이가 일치하지 않습니다");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return Objects.equals(token, token1.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}