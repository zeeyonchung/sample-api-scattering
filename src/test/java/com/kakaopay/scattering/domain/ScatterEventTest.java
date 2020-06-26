package com.kakaopay.scattering.domain;

import com.kakaopay.scattering.domain.exception.AlreadyFinishedException;
import com.kakaopay.scattering.domain.exception.NotSameRoomException;
import com.kakaopay.scattering.domain.exception.SelfReceiveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ScatterEventTest {

    private ScatterEvent scatterEvent;
    private String roomId;
    private Long userId;
    private LocalDateTime now;

    @BeforeEach
    void setValidEvent() {
        roomId = "room1";
        userId = 1L;
        now = LocalDateTime.of(2020, 6, 1, 7, 10);

        Token token = Token.of("123");
        Scatterer scatterer = new Scatterer(userId);
        ScatteredMonies scatteredMonies = ScatteredMonies.of(Arrays.asList(
                ScatteredMoney.of(1000), ScatteredMoney.of(1000), ScatteredMoney.of(1001)));

        this.scatterEvent = ScatterEvent.builder()
                    .token(token)
                    .roomId(roomId)
                    .scatteredMonies(scatteredMonies)
                    .scatterer(scatterer)
                    .createdDate(now)
                    .build();
    }

    @DisplayName("대화방이 다르면 NotSameRoomException")
    @Test
    void assign_notSameRoom() {
        assertThatThrownBy(() -> scatterEvent.assignOne(now, "room2", 2L))
                .isInstanceOf(NotSameRoomException.class);
    }

    @DisplayName("뿌린 사람과 받으려는 사람이 같으면 SelfReceiveException")
    @Test
    void assign_selfReceive() {
        assertThatThrownBy(() -> scatterEvent.assignOne(now, roomId, userId))
                .isInstanceOf(SelfReceiveException.class);
    }

    @DisplayName("뿌린지 10분이 지났으면 AlreadyFinishedException")
    @Test
    void assign_after10Minutes() {
        assertThatThrownBy(() -> scatterEvent.assignOne(now.plusMinutes(11), roomId, 2L))
                .isInstanceOf(AlreadyFinishedException.class);
    }
}
