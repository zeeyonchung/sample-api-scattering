package com.kakaopay.scattering.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.scattering.application.ReceiveMoneyService;
import com.kakaopay.scattering.application.ReceiveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReceiveMoneyController.class)
public class ReceiveMoneyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReceiveMoneyService receiveMoneyService;

    private ReceiveRequest successRequest;
    private String token;
    private Long userId;
    private String roomId;

    @BeforeEach
    void setUp() {
        this.token = "Zx3";
        this.userId = 12345L;
        this.roomId = "room13";

        this.successRequest = ReceiveRequest.builder()
                .token(token)
                .userId(userId)
                .roomId(roomId)
                .build();
    }

    @DisplayName("받기 성공 - 받은 금액 반환")
    @Test
    void receive_success() throws Exception {
        given(receiveMoneyService.receive(successRequest))
                .willReturn(500L);

        mockMvc.perform(patch("/scatter")
                    .header("X-USER-ID", userId)
                    .header("X-ROOM-ID", roomId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(successRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.receivedMoney").value(500L));
    }
}
