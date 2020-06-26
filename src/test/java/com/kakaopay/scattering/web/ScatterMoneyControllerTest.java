package com.kakaopay.scattering.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.scattering.application.ScatterMoneyService;
import com.kakaopay.scattering.application.ScatterRequest;
import com.kakaopay.scattering.domain.Scatterer;
import com.kakaopay.scattering.domain.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
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

    private ScatterRequest successRequest;
    private String token;
    private Long userId;
    private String roomId;

    @BeforeEach
    void setUp() {
        this.successRequest = ScatterRequest.builder()
                .money(10_555L)
                .receiverCount(10)
                .scatterer(new Scatterer(12345L))
                .build();

        this.token = "Zx3";
        this.userId = 12345L;
        this.roomId = "room13";
    }

    @DisplayName("뿌리기 성공 - 토큰 반환")
    @Test
    void scatter_success() throws Exception {
        given(scatterMoneyService.scatter(successRequest))
                .willReturn(Token.of(token));

        mockMvc.perform(post("/scatter")
                    .header("X-USER-ID", userId)
                    .header("X-ROOM-ID", roomId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(successRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

}
