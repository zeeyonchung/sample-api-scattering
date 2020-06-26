package com.kakaopay.scattering.infra.domain;

import com.kakaopay.scattering.domain.ScatteredMoney;
import com.kakaopay.scattering.domain.ScatteredMonies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

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

        assertThat(sum.isEqualMoney(money));
    }

    @DisplayName("금액을 균등하게 나눈다")
    @ParameterizedTest
    @CsvSource({"0, 389", "1, 389", "2, 389", "3, 388"})
    void divide(int index, long expected) {
        long money = 1555;
        int count = 4;

        ScatteredMonies scatteredMonies = divideStrategy.divide(money, count);
        List<ScatteredMoney> moneyList = scatteredMonies.getContent();

        assertThat(moneyList.get(index).isEqualMoney(expected)).isTrue();
    }
}
