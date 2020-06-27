package com.kakaopay.scattering.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.scattering.application.ReceiveMoneyService;
import com.kakaopay.scattering.application.ReceiveRequest;
import com.kakaopay.scattering.application.ScatterMoneyService;
import com.kakaopay.scattering.application.ScatterRequest;
import com.kakaopay.scattering.domain.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScatterMoneyController.class)
public class ScatterMoneyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ScatterMoneyService scatterMoneyService;

    @MockBean
    private ReceiveMoneyService receiveMoneyService;

    private String token;
    private Long userId;
    private String roomId;

    @BeforeEach
    void setUp() {
        this.token = "Zx3";
        this.userId = 12345L;
        this.roomId = "room13";
    }

    @DisplayName("뿌리기 성공 - 토큰 반환")
    @Test
    void scatter_success() throws Exception {
        ScatterRequest successScatterRequest = ScatterRequest.builder()
                .money(10_555L)
                .receiverCount(10)
                .userId(userId)
                .roomId(roomId)
                .build();

        given(scatterMoneyService.scatter(successScatterRequest))
                .willReturn(Token.of(token));

        mockMvc.perform(post("/scatter")
                    .header("X-USER-ID", userId)
                    .header("X-ROOM-ID", roomId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(successScatterRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @DisplayName("X-USER-ID 헤더가 없으면 BadRequestException")
    @Test
    void scatter_noUserIdHeader() throws Exception {
        ScatterRequest successScatterRequest = ScatterRequest.builder()
                .money(10_555L)
                .receiverCount(10)
                .userId(userId)
                .roomId(roomId)
                .build();

        mockMvc.perform(post("/scatter")
                .header("X-ROOM-ID", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(successScatterRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }

    @DisplayName("X-ROOM-ID 헤더가 없으면 BadRequestException")
    @Test
    void scatter_noRoomIdHeader() throws Exception {
        ScatterRequest successScatterRequest = ScatterRequest.builder()
                .money(10_555L)
                .receiverCount(10)
                .userId(userId)
                .roomId(roomId)
                .build();

        mockMvc.perform(post("/scatter")
                .header("X-USER-ID", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(successScatterRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }

    @DisplayName("받기 성공 - 받은 금액 반환")
    @Test
    void receive_success() throws Exception {
        ReceiveRequest successReceiveRequest = ReceiveRequest.builder()
                .token(token)
                .userId(userId)
                .roomId(roomId)
                .build();

        given(receiveMoneyService.receive(successReceiveRequest))
                .willReturn(500L);

        mockMvc.perform(patch("/scatter")
                .header("X-USER-ID", userId)
                .header("X-ROOM-ID", roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(successReceiveRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.receivedMoney").value(500L));
    }
}
