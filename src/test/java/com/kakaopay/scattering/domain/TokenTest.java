package com.kakaopay.scattering.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TokenTest {

    @DisplayName("객체 생성 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"123", "ABC", "abc", "1Ab"})
    void of(String input) {
        Token token = Token.of(input);
        assertThat(token).isEqualTo(Token.of(input));
    }

    @DisplayName("지정된 길이의 문자열이 아니면 IllegalArgumentException")
    @ParameterizedTest
    @ValueSource(strings = {"ab", "abcd"})
    void of_notMatchedLengthString(String input) {
        assertThatThrownBy(() -> Token.of(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("길이");
    }
}
