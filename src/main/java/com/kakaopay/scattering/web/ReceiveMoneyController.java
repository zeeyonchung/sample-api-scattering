package com.kakaopay.scattering.web;

import com.kakaopay.scattering.application.ReceiveMoneyService;
import com.kakaopay.scattering.application.ReceiveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReceiveMoneyController {

    private final ReceiveMoneyService receiveMoneyService;

    @PatchMapping("/scatter")
    public ResponseEntity<ReceiveResponse> receive(@RequestHeader("X-USER-ID") Long userId,
                                                   @RequestHeader("X-ROOM-ID") String roomId,
                                                   @RequestBody ReceiveRequest request) {
        request.setUserIdAndRoomId(userId, roomId);
        long receivedMoney = receiveMoneyService.receive(request);

        ReceiveResponse response = ReceiveResponse.builder()
                .receivedMoney(receivedMoney)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
