package com.kakaopay.scattering.application;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ScatterEventDetailRequest {

    private String token;
    private Long userId;
    private String roomId;

    public void setUserIdAndRoomId(Long userId, String roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }
}
