package com.kakaopay.scattering.web;

import com.kakaopay.scattering.application.ScatterMoneyService;
import com.kakaopay.scattering.application.ScatterRequest;
import com.kakaopay.scattering.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScatterMoneyController {

    private final ScatterMoneyService scatterMoneyService;

    @PostMapping("/scatter")
    public ResponseEntity<ScatterResponse> scatter(@RequestHeader("X-USER-ID") Long userId,
                                                   @RequestHeader("X-ROOM-ID") String roomId,
                                                   @RequestBody ScatterRequest request) {

        request.setUserIdAndRoomId(userId, roomId);
        Token token = scatterMoneyService.scatter(request);

        ScatterResponse response = ScatterResponse.builder()
                .token(token.getValue())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
