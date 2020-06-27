package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class ReceiveMoneyServiceTest {

    @Autowired
    private ReceiveMoneyService receiveMoneyService;

    @Autowired
    private ScatterEventRepo scatterEventRepo;

    @BeforeEach
    void setUp() {
        scatterEventRepo.deleteAll();
    }

    @DisplayName("할당된 금액을 리턴한다")
    @Test
    void receive_returnMoney() {
        ScatteredMonies monies = ScatteredMonies.of(Arrays.asList(
                ScatteredMoney.of(100L),
                ScatteredMoney.of(200L)));

        LocalDateTime createdDate = LocalDateTime.of(2020, 6, 1, 12, 10);

        ScatterEvent event = ScatterEvent.builder()
                .token(Token.of("123"))
                .roomId("room")
                .scatterer(new Scatterer(1L))
                .scatteredMonies(monies)
                .createdDate(createdDate)
                .build();

        scatterEventRepo.save(event);

        ReceiveRequest request = ReceiveRequest.builder()
                .token("123")
                .userId(2L)
                .roomId("room")
                .build();

        long receivedMoney = receiveMoneyService.receive(request);

        assertThat(receivedMoney).isEqualTo(100L);
    }

    @Transactional
    @DisplayName("할당된 금액의 할당 여부는 true")
    @Test
    void receive_assignMoney() {
        ScatteredMonies monies = ScatteredMonies.of(Arrays.asList(
                ScatteredMoney.of(100L),
                ScatteredMoney.of(200L)));

        LocalDateTime createdDate = LocalDateTime.of(2020, 6, 1, 12, 10);

        ScatterEvent event = ScatterEvent.builder()
                .token(Token.of("123"))
                .roomId("room")
                .scatterer(new Scatterer(1L))
                .scatteredMonies(monies)
                .createdDate(createdDate)
                .build();

        scatterEventRepo.save(event);

        ReceiveRequest request = ReceiveRequest.builder()
                .token("123")
                .userId(2L)
                .roomId("room")
                .build();

        receiveMoneyService.receive(request);

        ScatterEvent findEvent = scatterEventRepo.findByTokenAndRoomId(Token.of("123"), "room").get();
        List<ScatteredMoney> moneyList = findEvent.getScatteredMonies().getContent();

        assertAll(
                () -> assertThat(moneyList.get(0).isAssigned()).isTrue(),
                () -> assertThat(moneyList.get(1).isAssigned()).isFalse());
    }
}
