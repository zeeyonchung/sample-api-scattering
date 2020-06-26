package com.kakaopay.scattering.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenGeneratorTest {

    private TokenGenerator generator;

    @BeforeEach
    void setUp() {
        this.generator = new TokenGenerator();
    }

    @DisplayName("토큰을 생성한다")
    @Test
    void generate() {
        Token token = generator.generate();
        assertThat(token).isNotNull();
    }
}
