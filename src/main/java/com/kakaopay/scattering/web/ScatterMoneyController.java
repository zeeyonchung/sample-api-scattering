package com.kakaopay.scattering.web;

import com.kakaopay.scattering.application.*;
import com.kakaopay.scattering.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScatterMoneyController {

    private final ScatterMoneyService scatterMoneyService;
    private final ReceiveMoneyService receiveMoneyService;
    private final ScatterEventService scatterEventService;

    @PostMapping("/scatter")
    public ResponseEntity<ScatterResponse> scatter(@RequestHeader("X-USER-ID") Long userId,
                                                   @RequestHeader("X-ROOM-ID") String roomId,
                                                   @RequestBody ScatterRequest request) {

        request.setUserIdAndRoomId(userId, roomId);
        Token token = scatterMoneyService.scatter(request);

        ScatterResponse response = ScatterResponse.builder()
                .token(token.getValue())
                .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/scatter")
    public ResponseEntity<ReceiveResponse> receive(@RequestHeader("X-USER-ID") Long userId,
                                                   @RequestHeader("X-ROOM-ID") String roomId,
                                                   @RequestBody ReceiveRequest request) {
        request.setUserIdAndRoomId(userId, roomId);
        long receivedMoney = receiveMoneyService.receive(request);

        ReceiveResponse response = ReceiveResponse.builder()
                .receivedMoney(receivedMoney)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/scatter")
    public ResponseEntity<ScatterEventDetail> detail(@RequestHeader("X-USER-ID") Long userId,
                                                     @RequestHeader("X-ROOM-ID") String roomId,
                                                     @ModelAttribute ScatterEventDetailRequest request) {

        request.setUserIdAndRoomId(userId, roomId);
        ScatterEventDetail response = scatterEventService.detail(request);

        return ResponseEntity.ok(response);
    }
}
