package com.kakaopay.scattering.infra.domain;

import com.kakaopay.scattering.domain.ScatteredMoney;
import com.kakaopay.scattering.domain.ScatteredMonies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyEvenDivideStrategyTest {

    private MoneyEvenDivideStrategy divideStrategy;

    @BeforeEach
    void setUp() {
        this.divideStrategy = new MoneyEvenDivideStrategy();
    }

    @DisplayName("금액을 주어진 수량대로 나눈다")
    @ParameterizedTest
    @CsvSource({"1000, 1", "1000, 2"})
    void divide_count(long money, int count) {
        ScatteredMonies scatteredMonies = divideStrategy.divide(money, count);
        assertThat(scatteredMonies.size()).isEqualTo(count);
    }

    @DisplayName("나눠진 금액의 합계는 주어진 금액과 같다")
    @ParameterizedTest
    @CsvSource({"1000,1", "1000, 3", "1555, 4"})
    void divide_sum(long money, int count) {
        ScatteredMonies scatteredMonies = divideStrategy.divide(money, count);
        ScatteredMoney sum = scatteredMonies.sum();

        assertThat(sum.getMoney()).isEqualTo(money);
    }

    @DisplayName("금액을 균등하게 나눈다")
    @ParameterizedTest
    @MethodSource("divideArguments")
    void divide(long money, int count, ScatteredMonies expected) {
        ScatteredMonies scatteredMonies = divideStrategy.divide(money, count);
        assertThat(scatteredMonies).isEqualTo(expected);
    }

    public static Stream<Arguments> divideArguments() {
        long money1 = 1000;
        int count1 = 3;
        ScatteredMonies expected1 = ScatteredMonies.of(Arrays.asList(
                ScatteredMoney.of(334),
                ScatteredMoney.of(333),
                ScatteredMoney.of(333)));

        long money2 = 1555;
        int count2 = 4;
        ScatteredMonies expected2 = ScatteredMonies.of(Arrays.asList(
                ScatteredMoney.of(389),
                ScatteredMoney.of(389),
                ScatteredMoney.of(389),
                ScatteredMoney.of(388)));

        return Stream.of(
                Arguments.of(money1, count1, expected1),
                Arguments.of(money2, count2, expected2));
    }
}
