package com.kakaopay.scattering.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ScattererTest {

    @DisplayName("주어진 사용자와 같은 사용자인지 확인한다")
    @ParameterizedTest
    @CsvSource({"1, 1, true", "1, 2, false"})
    void isEqualMember(Long userId, Long anotherUserId, boolean expected) {
        Scatterer scatterer = new Scatterer(userId);
        Scatterer another = new Scatterer(anotherUserId);

        assertThat(scatterer.equals(another)).isEqualTo(expected);
    }
}
