package com.kakaopay.scattering.domain;

import java.util.Random;

public class TokenGenerator {

    private static final String CANDIDATE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghizklmnopqrstuvwxyz1234567890";

    private final Random random = new Random();

    public Token generate() {
        return Token.of(generateRandomly());
    }

    private String generateRandomly() {
        StringBuilder token = new StringBuilder();

        while (token.length() < Token.LENGTH) {
            int index = random.nextInt(CANDIDATE_CHARS.length());
            token.append(CANDIDATE_CHARS.charAt(index));
        }

        return token.toString();
    }
}
