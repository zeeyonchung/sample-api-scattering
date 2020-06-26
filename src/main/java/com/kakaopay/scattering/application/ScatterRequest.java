package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.Scatterer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScatterRequest {

    private long money;
    private Scatterer scatterer;
    private int receiverCount;

}
