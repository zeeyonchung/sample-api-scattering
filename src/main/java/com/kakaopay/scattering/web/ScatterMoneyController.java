package com.kakaopay.scattering.web;

import com.kakaopay.scattering.application.*;
import com.kakaopay.scattering.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScatterMoneyController {
    private static final String HEADER_USER_ID = "X-USER-ID";
    private static final String HEADER_ROOM_ID = "X-ROOM-ID";

    private final ScatterMoneyService scatterMoneyService;
    private final ReceiveMoneyService receiveMoneyService;
    private final ScatterEventService scatterEventService;

    @PostMapping("/scatter")
    public ResponseEntity<ScatterResponse> scatter(@RequestHeader(HEADER_USER_ID) Long userId,
                                                   @RequestHeader(HEADER_ROOM_ID) String roomId,
                                                   @RequestBody ScatterRequest request) {
        request.setUserIdAndRoomId(userId, roomId);
        Token token = scatterMoneyService.scatter(request);
        return ResponseEntity.ok(new ScatterResponse(token));
    }

    @PatchMapping("/scatter")
    public ResponseEntity<ReceiveResponse> receive(@RequestHeader(HEADER_USER_ID) Long userId,
                                                   @RequestHeader(HEADER_ROOM_ID) String roomId,
                                                   @RequestBody ReceiveRequest request) {
        request.setUserIdAndRoomId(userId, roomId);
        long receivedMoney = receiveMoneyService.receive(request);
        return ResponseEntity.ok(new ReceiveResponse(receivedMoney));
    }

    @GetMapping("/scatter")
    public ResponseEntity<ScatterEventDetail> detail(@RequestHeader(HEADER_USER_ID) Long userId,
                                                     @RequestHeader(HEADER_ROOM_ID) String roomId,
                                                     @ModelAttribute ScatterEventDetailRequest request) {
        request.setUserIdAndRoomId(userId, roomId);
        ScatterEventDetail response = scatterEventService.detail(request);
        return ResponseEntity.ok(response);
    }
}
