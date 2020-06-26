package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.Receiver;
import com.kakaopay.scattering.domain.Scatterer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScatterRequest {

    private long money;
    private List<Receiver> receivers;
    private Scatterer scatterer;

    public int getReceiversCount() {
        return receivers.size();
    }
}
