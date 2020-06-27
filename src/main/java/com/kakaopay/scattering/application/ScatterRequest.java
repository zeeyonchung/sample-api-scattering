package com.kakaopay.scattering.application;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScatterRequest {

    private long money;
    private int receiverCount;
    private Long userId;
    private String roomId;

    public void setUserIdAndRoomId(Long userId, String roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }
}
