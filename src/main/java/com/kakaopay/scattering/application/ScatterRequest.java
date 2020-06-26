package com.kakaopay.scattering.application;

import com.kakaopay.scattering.domain.Scatterer;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScatterRequest {

    private long money;
    private int receiverCount;

    @Setter
    private Scatterer scatterer;

}
